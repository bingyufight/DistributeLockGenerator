这是一个基于zookeeper和redis上的分布式锁，用于在跨机器或是跨JVM上进行分布式资源的锁管理，确保部署在多台机器或是JVM上的应用程序在同一时刻只能有一台机器或JVM获取分布式锁。
应用场景:1.某些离线定时任务做多机部署。如果不用分布式锁按照原来的方式进行部署，就会遇到在一定的间隔时间内，可能出现多次重复调用的问题。分布式锁保证只有一个机器上离线程序执行.
         2.分布式补偿框架和分布式事务中保证全局事务的有序性
		 3.其他场景:部署在不同机器上的程序需要对共享资源的互斥访问 。。。。。
		 
<h1>分布式锁的基本组成模块</h1>

	zookeeper分布式锁
	redis分布式锁	
    配置管理
    JDK 动态代理 + 注解（实现一个类似于Spring的aop 实现将各个机器分别操作分布式锁的log信息保存进mysql或是mongo）（这个log信息未来可以用来实现优先级抢占式分布式锁)
	自定义线程池
	mybatis操作mysql	
    mongo db操作
	Unit test模块
 
 
<h1>分布式锁的核心接口</h1>

	public interface IDistributeLock {
		
	boolean acquireLock() throws DistributeLockException;
	
	boolean releaseLock() throws DistributeLockException;
	
	boolean isLocked() throws DistributeLockException;
	
	boolean tryAcquireLock(long duration) throws DistributeLockException;
   }

<h1> JDK 动态代理 + 自定义注解 实现aop记录各台机器获取分布式锁的log信息</h1>

1 自定义注解

	@Retention(RetentionPolicy.RUNTIME) 
	@Target(ElementType.METHOD)
	public @interface DistributeLockAopTag {
		public Class defaultAopHandleClass();
	}
 
2 jdk 动态代理类

	public class DistributeLockAopInvocationHandler implements InvocationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DistributeLockAopInvocationHandler.class);

	private Object proxyTarget;
	
	private AbstractDistributeLockAopEntity proxyDistributeLockAopEntity;
	
	public DistributeLockAopInvocationHandler(Object target,AbstractDistributeLockAopEntity proxyDistributeLockAopEntity){
		proxyTarget = target;
		this.proxyDistributeLockAopEntity = proxyDistributeLockAopEntity;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		  Method originalMethod = proxyTarget.getClass().getMethod(method.getName(), method.getParameterTypes());
		  if (!originalMethod.isAnnotationPresent(DistributeLockAopTag.class)) {
		      return method.invoke(proxyTarget, args);
		  }
		  Object result = method.invoke(proxyTarget, args);
		  Map<String,Object> params = generateParamsFromTargetObject(originalMethod.getName(),result);
		  proxyDistributeLockAopEntity.logDistributeLockInfo(params);
		  return result;			
	}
	
 

