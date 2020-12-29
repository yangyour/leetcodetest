## 1、前言
老板说，明天甲方要来看产品，你得造点数据，而且数据必须是“真”的，演示效果要好看一些，这样他才会买我们的产品，我好明年给你换个嫂子。一般开发接到这种过分要求都不会很乐意去做，这完全是体力劳动，而且很棘手。今天胖哥教你一招，让你做出逼真的“假”数据。
## 2、javafaker
我们java是有可以生成仿真数据的框架的，这里我安利一个Javafaker框架，你只需要在
项目中引入:
```
    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </devpendency>
```
> 忘记说了它主要的使用场景就是为了制造仿真测试数据，而并不是用来造假。

然后来演示一下它的用法，先定义一个Java Bean:
```java
/**
 * User info.
 *
 * @author felord.cn
 * @since 10 :50
 */
@Data
public class UserInfo {
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 手机
   */
  private String cellPhone;
  /**
   * 大学
   */
  private String universityName;
  /**
   * 城市
   */
  private String city;
  /**
   * 地址
   */
  private String street;
}
```
然后声明一个Faker对象，你可以根据需要进行本地声明，如果是中国的:
```java
    Faker fakerWithCN = new Faker(Locale.CHINA);
```
你要是想造点美帝的数据就改成这样:
```java
    Faker fakerWithUS = new Faker(Locale.US);
```
这里我们使用中国来生成模拟10条仿真数据
```java
for (int i = 0; i < 10; i++) {

    UserInfo userInfo = new UserInfo();

    userInfo.setRealName(fakerWithCN.name().fullName());
    userInfo.setCellPhone(fakerWithCN.phoneNumber().cellPhone());
    userInfo.setCity(fakerWithCN.address().city());
    userInfo.setStreet(fakerWithCN.address().streetAddress());
    userInfo.setUniversityName(fakerWithCN.university().name());
    System.out.println("userInfo = " + userInfo);

}
```
我们来看下结果:
![](https://segmentfault.com/img/bVcMAbY)
