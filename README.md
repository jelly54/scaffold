利用 `maven` 的 `archetype` 制作的 `spring boot` 项目脚手架

# 安装本项目到本地archetype

```shell script
git clone git@github.com:jelly54/scaffold.git

cd scaffold

mvn clean install
```

# 生成新的脚手架

在新开的项目路径下执行以下命令

```shell script
mvn archetype:generate \
-DgroupId=com.jelly.boot \
-DartifactId=demo \
-Dversion=1.0.0-SNAPSHOT \
-DarchetypeGroupId=com.jelly \
-DarchetypeArtifactId=scaffold \
-DarchetypeVersion=1.0 -X -DarchetypeCatalog=local 
```

# 功能点

## 业界较为流行的模块划分

- api 所有接口包文件，可以单独打包对外提供服务
- common 抽象基础服务下沉，如调用第三方服务、本系统各模块通用服务
- core 本系统核心业务模块
- web 本系统web各种配置模块，启动类，启动配置项等

## 出参、入参包装（可以开启和关闭）

### 入参

默认开启 params 封装入参，可以在spring boot配置中增加如下配置，并设为false

```shell script
request.body.params.enable=false 
```

或者在controller 类上增加 `@IgnoreRequestInput` 注解

#### post 请求(封装params包装，封装result返回体)

本地启动之后调用

```shell script
POST http://localhost:9980/bind
```

入参

>Content-Type:application/json

```shell script
{
  "params":{
    "id":133,
    "name": "姓名",
    "dept_name":"这是一个部门"
  }
}
```

返回数据

```shell script
{
    "success": true,
    "msg": "success",
    "code": 0,
    "data":{
        "id": 133,
        "name": "姓名",
        "dept_name": "这是一个部门"
    }
}
```

#### get 请求（出参result包装）

本地启动之后调用

```shell script
GET http://localhost:9980/hello
```

将会返回

```shell script
{
    "success": true,
    "msg": "success",
    "code": 0,
    "data":{
        "deptName": "部门名称",
        "name": "galaxy",
        "age": "70"
    }
}
```

### 出参

默认开启 result 封装出参，可以在spring boot配置中增加如下配置，并设为false

```shell script
response.body.result.enable=false 
```

或者在需要忽略出参封装的controller方法上增加 `@IgnoreResponseResult` 注解

#### post 请求(封装params包装，无result封装)

本地启动之后调用

```shell script
POST http://localhost:9980/bind-non-result
```

入参

>Content-Type:application/json

```shell script
{
  "params":{
    "id":133
  }
}
```

返回数据

```shell script
{
   "id": 133,
   "name": "姓名",
   "dept_name": "这是一个部门"
}
```

## 日志aop记录

可以抓取当前时间、调用方法、uri、ip地址、出参、入参

如上请求的日志如下

```shell script
2020-11-09 16:21:54.987  INFO 8727 --- [nio-9980-exec-3] com.jelly.boot.aop.LoggerAspect          : requestId: 2c453d38-b4e0-414c-bcb6-e2367133bf62, start time: 1604910114987
2020-11-09 16:21:54.987  INFO 8727 --- [nio-9980-exec-3] com.jelly.boot.aop.LoggerAspect          : requestId: 2c453d38-b4e0-414c-bcb6-e2367133bf62, POST /bind, ip: 127.0.0.1
2020-11-09 16:21:54.987  INFO 8727 --- [nio-9980-exec-3] com.jelly.boot.aop.LoggerAspect          : requestId: 2c453d38-b4e0-414c-bcb6-e2367133bf62, class-method: Result com.jelly.boot.controller.OutPutTestController.dataBind(DemoRequest), args: [Demo{id=133, name='姓名', deptName='这是一个部门'}]
2020-11-09 16:21:54.987  INFO 8727 --- [nio-9980-exec-3] com.jelly.boot.aop.LoggerAspect          : requestId: 2c453d38-b4e0-414c-bcb6-e2367133bf62, class-method: Result com.jelly.boot.controller.OutPutTestController.dataBind(DemoRequest), result:com.jelly.boot.message.output.Result@3729c4e9
2020-11-09 16:21:54.987  INFO 8727 --- [nio-9980-exec-3] com.jelly.boot.aop.LoggerAspect          : requestId: 2c453d38-b4e0-414c-bcb6-e2367133bf62, end time: 1604910114987, cost:0ms
```

```shell script
2020-11-09 16:15:39.222  INFO 8727 --- [nio-9980-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-11-09 16:15:39.222  INFO 8727 --- [nio-9980-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-11-09 16:15:39.232  INFO 8727 --- [nio-9980-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 10 ms
2020-11-09 16:15:39.254  INFO 8727 --- [nio-9980-exec-1] com.jelly.boot.aop.LoggerAspect          : requestId: a2a45ee1-8364-40f3-a97d-e3bce852b6fe, start time: 1604909739254
2020-11-09 16:15:39.255  INFO 8727 --- [nio-9980-exec-1] com.jelly.boot.aop.LoggerAspect          : requestId: a2a45ee1-8364-40f3-a97d-e3bce852b6fe, GET /hello, ip: 127.0.0.1
2020-11-09 16:15:39.256  INFO 8727 --- [nio-9980-exec-1] com.jelly.boot.aop.LoggerAspect          : requestId: a2a45ee1-8364-40f3-a97d-e3bce852b6fe, class-method: HashMap com.jelly.boot.controller.OutPutTestController.hello(), args: []
2020-11-09 16:15:39.261  INFO 8727 --- [nio-9980-exec-1] com.jelly.boot.aop.LoggerAspect          : requestId: a2a45ee1-8364-40f3-a97d-e3bce852b6fe, class-method: HashMap com.jelly.boot.controller.OutPutTestController.hello(), result:{deptName=部门名称, name=galaxy, age=70}
2020-11-09 16:15:39.261  INFO 8727 --- [nio-9980-exec-1] com.jelly.boot.aop.LoggerAspect          : requestId: a2a45ee1-8364-40f3-a97d-e3bce852b6fe, end time: 1604909739261, cost:7ms
```

## 统一异常捕获、自定义系统异常

### 自定义异常捕获

本地启动之后调用

```shell script
GET http://localhost:9980/helloMyError
```

将会返回

```shell script
{
  "success": false,
  "msg": "非法访问,没有认证",
  "code": 10007,
  "data": null
}
```

### 系统异常捕获

```shell script
GET http://localhost:9980/helloError
```

将会返回

```shell script
{
  "success": false,
  "msg": "系统异常",
  "code": 10000,
  "data": null
}
```

- 分布式id生成器，雪花id生成器
- 时间工具类、本地host工具类、IP工具类、正则表达式工具类


