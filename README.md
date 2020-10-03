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

- 业界较为流行的模块划分
- 封装完善的出参、入参包装（可以开启和关闭）
- 统一异常捕获、自定义系统异常
- 分布式id生成器，雪花id生成器
- 时间工具类、本地host工具类、IP工具类、正则表达式工具类

