# 项目说明

## 一、项目运行步骤

### 1.下载IDEA

最好是付费版，申请学生账号

### 2.配置JDK

File -> Project Structure -> SDKs -> '+'->

Download JDK... -> 版本选1.8 ->Eclipse Temurin (AdoptOpenJDK HotSpot)

### 3.配置数据库
#### (1)下载并安装MySQL 8.0

#### (2)手动建数据库，初始化测试数据

使用src/main/resources/database.sql的语句初始化数据库

#### (3)配置项目连接数据库的账号密码

打开src/main/resources/application-datasource.yml，将username,password更改为自己数据库的密码，后期会把该文件gitignore掉

### 4.配置Redis

#### (1) 下载并配置启动Redis

#### (2) 试运行

尝试运行单元测试src/test/java/com/bytedance/accountsystem/mapper/RedisRepositoryTest.java，点击test1()方法前面的运行按钮，看到结果为先输出a, 2.5秒后输出null

### 5.运行项目
Run -> Run 'AccountSystemApplication' 即可启动项目

### 6.测试项目是否能正常运行
在浏览器输入http://localhost:8080/test/hello ，浏览器应返回
```json
{
    "data": {
        "id": 1,
        "msg": "Hello, World"
    },
    "metaInfo": {
        "msg": "查询成功",
        "status": 200
    }
}
```

## 二、项目结构说明
```
|-src (源代码)
  |-main (工程代码)
  | |-java (本体代码)
  | | |-com.bytedance.accountsystem (项目包名)
  | |   |-AccountSystemApplication.java (项目主程序(入口))
  | |   |-dto (Controller与前端的传输类)
  | |   |-controller (Controller层)
  | |   |-service (Service层)
  | |   |-mapper (DAO层)
  | |   |-entity (实体类(访问数据库的类))
  | |   |-utils (工具类)
  | |-resource (资源文件)
  |   |-static (静态文件)
  |   |-application.yml (SpringBoot主配置文件)
  |   |-application-*.yml (SpringBoot其余配置文件)
  |   |-database.sql (数据库建表文件)
  |-test (单元测试代码)
```

src/main/java/com.com.bytedance.accountsystem中为项目主体代码，目前有关test的类我已写好注释，如果有问题可以在群里问