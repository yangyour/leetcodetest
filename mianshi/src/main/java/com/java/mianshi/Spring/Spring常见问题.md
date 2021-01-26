## 1. 什么是Spring框架?
Spring是一种轻量级开发框架，旨在提高开发人员的开发效率以及系统的可维护性。Spring官网：
(https://spring.io/)[https://spring.io/]。

我们一般说Spring框架指的都是Spring Framework，它是很多模块的集合，使用这些模块可以很
方便地协助我们进行开发。这些模块是：核心容器、数据访问/集成，Web、AOP(面向切面编程)、工具、
消息和测试模块。比如：Core Container组件是Spring所有组件的核心，Beans组件和Context
组件是实现IOC和依赖注入的基础，AOP组件用来实现面向切面编程。

Spring官网列出的Spring的6个特征：
* 核心技术：依赖注入(DI)，AOP，事件(events)，资源，i18n，验证，数据绑定，类型转换，SpEL。
* 测试：模拟对象，TestContext框架，SpringMVC测试，WebTestClient。
* 数据访问：事务，DAO支持，JDBC，ORM，编组XML。
* Web支持：Spring MVC和Spring WebFlux Web框架。
* 继承：远程处理，JMS，JCA，JMX，电子邮件，任务，调度，缓存。
* 语言：Kotlin，Groovy，动态语言
## 2、例举一些重要的Spring模块？
下图对应的Spring4.x版本。目前最新的5.x版本中的Web模块的Portlet组件已经被废弃掉，同时
增加了用于异步响应式处理的WebFlux组件。
![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-6/Spring%E4%B8%BB%E8%A6%81%E6%A8%A1%E5%9D%97.png)
* Spring Core:基础，可以说Spring其他所有的功能都需要依赖于该类库。主要提供IOC依赖注入功能
* Spring Aspects:该模块为与AspectJ的集成提供支持。
* Spring Aop：提供了面向切面的编程实现。
* Spring JDBC:JAVA数据库连接。
* Spring JMS:JAVA消息服务。
* Spring ORM：用于支持Hibernate等ORM工具。
* Spring Web：为创建Web应用程序提供支持。
* Spring Test：提供了对JUnit和TestNG测试的支持。
## 3、 @RestController VS @Controller
`Controller`返回一个页面
单独使用`@Controller`不加`@ResponceBody`的话一般使用在要返回一个视图的情况，这种情况
属于比较传统的Spring MVC应用，对应与前后端不分离的情况。
![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/SpringMVC%E4%BC%A0%E7%BB%9F%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B.png)
`@RestController`返回JSON或XML形式数据

但`@RestController`只返回对象，对象数据直接以JSON或XML形式写入HTTP响应(Response)
中，这种情况属于RESTful Web服务，这也是目前日常开发所接触的最常用的情况(前后端分离)。
![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/SpringMVCRestController.png)
`@Controller + @ResponseBody`返回JSON或XML形式数据

如果你需要在Spring4之前开发RESTful Web服务的话，你需要使用`@Controller`并结合
`@ResponseBody`注解，也就是说`@Controller + @ResponseBody = @RestController`
(Spring 4之后新加的注解)。
> `@ResponseBody`注解的作用是将`@Controller`的方法返回的对象通过适当的转换器转换
>为指定的格式之后，写入到HTTP响应(Response)对象的body中，通常用来返JSON或者XML数据，
>返回JSON数据的情况比较多。

![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/Spring3.xMVCRESTfulWeb%E6%9C%8D%E5%8A%A1%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B.png)
## 4、Spring IOC & AOP
### 4.1、谈谈自己对于Spring IOC和AOP的理解
#### IOC
IOC(Inverse of Control:控制反转)是一种设计思想，就是将原本在程序中手动创建对象的控制
权，交由Spring框架来管理。IoC在其他语言中也有应用，并非Spring特有。IoC容器是Spring
用来实现IoC的载体，IoC容器实际上就是个Map(Key,value),Map中存放的是各种对象。

将对象之间的相互依赖关系交给IoC容器来管理，并由IoC容器完成对象的注入。这样可以极大程度
上简化应用的开发，把应用从复杂的依赖关系中解放出来。IoC容器就像一个工厂一样，当我们需要
创建一个对象的时候，只需要配置好配置文件/注解即可，完全不用考虑对象是如何被创建出来的。
在实际项目中一个Service类可能有几百甚至上千个类作为它的底层，假如我们需要实例化这个
Service，你可能要每次都搞清这个Service所有底层类的构造函数，这可能会把人逼疯。如果
利用IoC的话，你只需要配置好，然后在需要的地方引用就行了，这大大增加了项目的可维护性且降低
了开发难度。

Spring时代我们一般用一个XML文件来配置Bean，后来开发人员觉得XML文件来配置不太好，于是
SpringBoot注解配置就慢慢流行起来。

推荐阅读：[https://www.zhihu.com/question/23277575/answer/169698662](https://www.zhihu.com/question/23277575/answer/169698662)

##### Spring IoC的初始化过程：
![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/SpringIOC%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B.png) 
IoC源码阅读：
* https://javadoop.com/post/spring-ioc
#### AOP
AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，却为业务模块
所共同调用的逻辑或责任(例如事务处理、日志管理、权限控制等)封装起来，便于减少系统的重复
代码，降低模块间的耦合度，并有利于未来的可扩展性和可维护性。

Spring AOP就是基于动态代理的，如果要代理的对象，实现了某个接口，那么Spring AOP会使用
JDK Proxy，去创建代理对象，而对于没有实现接口的对象，就无法使用JDK Proxy去进行代理了，
这时候Spring AOP会使用Cglib，时候Spring AOP会使用Cglib生成一个被代理对象的子类来
作为代理，如下图所示：
![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-6/SpringAOPProcess.jpg)
当然你也可以使用AspectJ，Spring AOP已经集成了AspectJ，AspectJ应该算的上是Java生态
系统中最完整的AOP框架了。

使用AOP之后我们可以把一些通用功能抽象出来，在需要用到的地方直接使用即可，这样大大简化了
代码量。我们需要增加新功能时也方便，这样也提高了系统扩展性。日志功能、事务管理等等场景
都用到了AOP。
### 4.2、Spring AOP和Aspect AOP有什么区别？
Spring AOP属于运行时增强，而Aspect AOP是编译时增强。Spring AOP基于代理(Proxying),
而Aspect基于字节码操作(Bytecode Mainipulation)。

Spring AOP已经集成了AspectJ，AspectJ应该算的上是Java生态中最完整的AOP框架了。AspectJ
相比于Spring AOP功能更加强大，但是Spring AOP相对来说更简单。

如果我们的切面比较少，那么两者性能差异性不大。但是，当切面太多的话，最好选择AspectJ，
它比Spring AOP快很多，
### Spring Bean
#### 5.1 Spring中的Bean的作用域有哪些？
* singleton：唯一bean实例，Spring中的bean默认都是单例的。
* prototype：每次请求都会创建一个新的bean实例。
* request：每一次HTTP请求都会产生一个新的bean，该bean仅在当前的HTTP request内有效
* session：每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP session内有效。
* global-session：全局session作用域，仅仅基于portlet的web应用中才有意义，Spring5
已经没有了。Portlet是能够生成语义代码(例如：HTML)片段的小型Java Web插件。它们基于
portlet容器，可以像servlet一样处理HTTP请求。但是，与Servlet不同，每个portlet都有不同的会话。

#### 5.2 Spring中的单例bean的线程安全问题了解吗？
大部分时候我们并没有在系统中使用多线程，所以很少有人会关注这个问题。单例bean存在线程
问题，主要是因为当多个线程操作同一个对象的时候，对这个对象的非静态成员变量的写操作会
存在线程安全问题。

常见的有两种解决办法：
1. 在Bean对象中尽量避免定义可变的成员变量(不太现实)。
2. 在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在ThreadLocal中(推荐的一种方式)
#### 5.3 @Component和@Bean的区别是什么？
1. 作用对象不同，`@Component`注解作用于类，而`@Bean`注解作用于方法。
2. `@Component`通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中,(我们可以
使用`@ComponentScan`注解定义要扫描的路径从中找出标识了需要装配的类自动装配到Spring的
bean容器中)。`@Bean`注解通常是我们在标有该注解的方法中定义产生这个Bean，`@Bean`
告诉了Spring这是某个类的实例，当我需要用它的时候还给我。
3. `@Bean`注解比`@Component`注解的自定义性更强，而且很多地方我们只能通过`@Bean`
注解来注册Bean。比如当我们引用第三方库中的类需要装配到`Spring`容器时，则只能通过
`@Bean`来实现。

`@Bean`注解使用示例：
```java
@Configuration
public class AppConfig{
    @Bean
    public TransferService transferService(){
        return new TransferServiceImpl();
    }
}
```
上面的代码相当于下面的xml配置
```xml
<beans>
    <bean id="transferService" class="com.acme.TransferServiceImpl"/>
</beans>
```
下面这个例子是通过`@Component`无法实现的。
```
@Bean
public OneService getService(status){
    case(status){
        when 1:
            return new serviceImpl1();
        when 2:
            return new serviceImpl2();
        when 3:
            return new serviceImpl3();
    }
}
```
### 5.4 将一个类声明为Spring的bean的注解有哪些？
我们一般使用`@Autowired`注解自动装配bean，要想把类表示成可用与`@Autowired`注解
自动装配的bean的类，采用以下注解可实现：
* @Component：通用的注解，可标注任意类为Spring组件。如果一个Bean不知道属于哪个层，
可用使用@Component注解标注。
* @Repository：对应持久层即Dao层，主要用于数据库相关操作。
* @Service：对应服务层，主要涉及一些复杂的逻辑，需要用到Dao层。
* @Controller:对应Spring MVC控制层，主要用于接受用户请求并调用Service层返回数据
给前端页面。
### 5.5 Spring中的bean生命周期？
* Bean容器找到配置文件中Spring Bean的定义。
* Bean容器利用Java Reflection API创建一个Bean的实例。
* 如果涉及到一些属性值 利用`set()`方法设置一些属性值。
* 如果Bean实现了`BeanNameAware`接口，调用`setBeanName()`方法，传入Bean的名字。
* 如果Bean实现了`BeanClassLoaderAware`接口，调用`setBeanClassLoader()`方法，
传入`ClassLoader`对象的实例。
* 与上面的类似，如果实现了其他`*.Aware`接口，就调用相应的方法。
* 如果有和加载这个Bean的Spring容器相关的`BeanPostProcessor`对象，执行`postProcessBeforeInitialization()`
方法
* 如果Bean实现了`@InitializingBean`接口，执行`afterPropertieSet()`方法。
* 如果Bean在配置文件中的定义包含init-method属性，执行指定的方法。
* 如果有和加载这个Bean的Spring容器相关的`BeanPostProcessor`对象，执行`postPorcessAfterInitialization()`
方法                                                                   
* 当要销毁Bean的时候，如果Bean实现了`DisposableBean`接口，执行`destroy()`方法。
* 当腰销毁Bean的时候，如果Bean在配置文件中的定义包含destroy-method属性，执行指定的方法。

图示：
![](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-9-17/48376272.jpg)
与之比较类似的中文版本：
![](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-9-17/5496407.jpg)
## 6. Spring MVC
### 6.1 说说对于Spring MVC了解？
谈到这个问题，我们不得不提提之前Model1和Model2这两个没有SpringMVC的时代。
* Model时代：很多学java后端比较晚的朋友可能并没有接触过Model模式下的JavaWeb应用开发。
在Model1模式下，整个web应用几乎全部用JSP页面组成，只用少量的JavaBean来处理数据库
连接、访问等操作，这个模式下JSP既是控制层又是表现层。显而易见，这种模式存在很多问题。
比如：①将控制逻辑和表现逻辑混杂在一起，导致代码重用率极低；②前端和后端相互依赖，难以
进行测试并且开发效率极低；
* Model2时代：学过Servlet并做过相关Demo的朋友应该了解"JavaBean(Model)+JSP(View)
+Servlet(Controller)"这种开发模式，这就是早期是JavaWeb MVC开发模式。Model：
系统涉及的数据，也就是dao和bean。View：展示模型中的数据，只是用来展示。Controller：
处理用户的请求，返回数据给JSP展示给用户。

Model2模式下还存在很多问题，Model2的抽象和封装程度还远远不够，使用Model2开发时不可
不可避免地会重复造轮子，这就大大降低了程序的可维护性和复用性。于是很多JavaWeb开发
相关的MVC框架应运而生比如Struts2，但是Struts2比较笨重。随着Spring轻量级开发框架的
流行，Spring生态圈出现了Spring MVC框架，Spring MVC是当前最优秀的MVC框架。相比
与Status2，Spring MVC使用更加简便和方便，开发效率更高，并且Spring MVC运行速度
更快。

MVC是一种设计模式，Spring MVC是一款很优秀的MVC框架。Spring MVC可以帮助我们进行
更简洁的Web层的开发，并且他天生与Spring框架集成。Spring MVC下我们一般把后端项目
分为Service层(处理业务)、Dao层(数据库操作)、Entity层(实体层)、Controller层(
控制层，返回数据给前台页面)。

**Spring MVC的简单原理如下：**
![](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-10-11/60679444.jpg)
### 6.2 Spring MVC的工作原理了解吗？
**原理如下图所示：**
![](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-10-11/49790288.jpg)
上图的一个小笔误问题：Spring MVC的入口函数也就是前端控制器`DispatcherServlet`
作用是接收请求，响应结果。

**流程说明：(重要)**
1. 客户端(浏览器)发送请求，直接请求到'DispatcherServlet'。
2. `DispatcherServlet`根据请求信息调用`HandlerMapping`,解析请求对应的`Handler`
3. 解析到对应的`Handler`(也就是我们平常说的`Handler`控制器)后，开始由`HandlerAdapter`
适配器处理。
4. `HandlerAdapter`会根据`Handler`来调用真正的处理器来处理请求，并处理相应的业务逻辑。
5. 处理器处理完业务后，会返回一个`ModelAndView`对象，`Model`是返回的数据对象，`View`
是个逻辑上的`View`。
6. `ViewReslover`会根据逻辑`View`查找实际的`View`。
7. `DispatcherServlet`把返回的`Model`传给`View`(视图渲染)。
8. 把`View`返回给请求者(浏览器)。

## 7、 Spring框架中用到了哪些设计模式？
关于下面一些设计模式的详细介绍，可以看[《面试官:“谈谈Spring中都用到了那些设计模式?”。》](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485303&idx=1&sn=9e4626a1e3f001f9b0d84a6fa0cff04a&chksm=cea248bcf9d5c1aaf48b67cc52bac74eb29d6037848d6cf213b0e5466f2d1fda970db700ba41&token=255050878&lang=zh_CN#rd)
* 工厂设计模式：Spring使用工厂模式通过`BeanFactory`、`ApplicationContext`创建Bean对象。
* 代理设计模式：SpringAOP功能的实现
* 单例设计模式：Spring中的Bean默认都是单例的。
* 模板方法模式：Spring中`jdbcTemplate`、`hibernateTemplate`等以Template
结尾的对数据库操作的类，它们就用到了模板模式。
* 包装器设计模式：我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要
会去访问不同的数据库。这种模式可以根据客户的需求能动态切换不同的数据源。
* 观察者模式：Spring事件驱动模型就是观察者模式很经典的一个应用。
* 适配器模式：SpringAOP的增强或通知(Advice)使用到了适配器模式、Spring MVC中
也是用到了适配器模式适配`Controller`。
* ......
## 8、 Spring事务
### 8.1、 Spring管理事务的方式有几种？
1. 编程式事务，在代码中硬编码。(不推荐使用)
2. 声明式事务，在配置文件中配置。(推荐使用)

**声明式事务又分为2种:**
1. 基于XML的声明式事务
2. 基于注解的声明式事务
### 8.2、Spring事务中的隔离级别有哪几种？
**TransactionDefinition接口中定义了五个表示隔离级别的常量：**
* TransactionDefinition.ISOLATION_DEFAULT:使用后端数据库默认的隔离级别，
Mysql默认采用的PEPEATABLE_READ隔离级别Oracle默认采用的READ_COMMITTED隔离界别。
* TransactionDefinition.ISOLATION_READ_UNCOMMITTED:最低的隔离级别，允许
读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读
* TransactionDefinition.ISOLATION_READ_COMMITTED:允许读取并发事务已经提交
的数据，可以阻止脏读，但是幻读和不可重复读仍有可能发生。
* TransactionDefinition.ISOLATION_REPEATABLE_READ:对同一字段的多次读取结果
都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能
发生。
* TransactionDefinition.ISOLATION_SERIALIZABLE:最高的隔离级别，完全服从
ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，
该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会
用到该级别。

### 8.3、 @Transactional(rollbackFor=Exception.class)注解了解吗？
我们知道：Exception分为运行时异常RuntimeException和非运行时异常。事务管理对于
企业级应用来说是至关重要的，及时出现异常情况，它也可以来保证数据的一致性。

当`@Transactional`注解作用于类上时，该类所有的public方法都将具有该类型的事务属性，
同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。如果类或者方法加了这个注解，
那么这个类里面方法抛出异常，就会回滚，数据库里面的数据也会回滚。

在`Transactional`注解中如果不配置`rollbackFor`属性，那么事务只会在遇到`RuntimeException`
的时候才会回滚，加上`rollbackFor=Exception.class`,可以让事务在遇到非运行时异常
也回滚。

关于`Transactional`注解推荐阅读的文章：
> [透彻的掌握 Spring 中@transactional 的使用](https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/index.html)


