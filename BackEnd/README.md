# 项目说明

### 一、项目后端代码结构说明

```
|-src (源代码)
  |-main
  | |-java
  | | |-com.bytedance.accountsystem (项目包名)
  | |   |-AccountSystemApplication.java (项目主程序(入口))
  | |   |-dto 				(Controller与前端的传输类)
  | |   |-controller 	(Controller层)
  | |   |-service 		(Service层)
  | |   |-mapper 			(DAO层)
  | |   |-entity 			(实体类)
  | |   |-utils 			(工具类)
  | |   |-annotation 	(注解)
  | |   |-aspect 			(Spring切面)
  | |   |-config 			(配置类)
  | |   |-interceptor (拦截器)
  | |   |-exception 	(异常类)
  | |-resource (资源文件)
  |   |-static (静态文件)
  |   |-application.yml (SpringBoot主配置文件)
  |   |-application-*.yml (SpringBoot其余配置文件)
  |   |-database.sql (数据库建表文件)
  |-test (单元测试代码)
```



### 二、系统设计

#### （1）功能说明

1. 系统具有注册、登录、登出、注销、生成图片验证码的功能
2. 系统具有风险功能检测功能，能对异常频率的请求进行拦截，并对异常登录做出验证码校验

#### （2）功能实现说明

1. 注册、登录、登出、注销功能
   1. 以User为实体类，通过Controller-Service-DAO三层实现功能的处理，对需要登录后访问的url，通过Spring AOP中的切面进行拦截
2. 图片验证码功能
   1. 技术实现：使用了github相关生成包，每一个图片验证码生成一个token，与正确验证码一起存储在Redis缓存中。后将图片与token同时下发，用户填写完验证码后携带token返回给后端，后端通过对比Redis中的正确验证码进行校验
   2. 特性：使用Spring AOP功能设计注解@CaptchaVerify，需要对验证码进行验证的方法（接口）仅需在方法前加上此注解，拦截器便可在访问资源前进行验证码参数的拦截与校验，不需要在方法里增加相关业务逻辑代码，符合高内聚，低耦合的设计理念。
3. 风险监测（异常频率请求）功能
   1. 逻辑处理：对于每个需要监测的每次请求，若距上次的请求时间 $ t_0 $<设定间隔时间$t_d$，则触发“需要验证码”（1级）。设定一个高等级风险触发周期$t_h$，如果在一定的$t_h$时间内触发1级的次数到达$n_2$次，且满足1级风险的触发条件，则触发“需要等待后再进行请求”（2级）；如果在一定的$t_h$时间内触发1级的次数到达$n_3$次，且满足1级风险的触发条件，则触发“禁止请求”（3级）；每触发一次2或3级风险，$t_h$重新从触发时间开始计时。
   2. 技术实现：
      1. 使用Redis记录上次请求时间，过期时间设定为$t_d$，即可获得两次请求的间隔时间
      2. 使用Redis记录风险触发次数，过期时间设定为$t_h$，即可获得高等级风险触发周期$t_h$，1级风险的触发次数
   3. 特性：使用Spring AOP功能设计注解@RiskDetect，需要进行风险监测的方法（接口）仅需在方法前加上此注解，拦截器便可对请求的进行拦截与风险校验，不需要在方法里增加相关业务逻辑代码，符合高内聚，低耦合的设计理念。
4. 异常登录
   1. 对于登录密码错误的请求，设置其需要验证码进行登录



### 三、编译运行

1. 安装JDK1.8，maven包管理器，MySQL8.0，Redis，Tomcat
2. 启动MySQL，Redis，Tomcat
3. 初始化数据库，将src/main/resource中database.sql 置于MySQL中执行
4. mvn clean install 编译打包项目
5. 将target目录下生成的war包置于Tomcat服务器中即可在localhost:8080访问