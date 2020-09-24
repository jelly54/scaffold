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
-DgroupId=com.jelly.test \
-DartifactId=demo \
-Dversion=1.0.0-SNAPSHOT \
-DarchetypeGroupId=com.jelly \
-DarchetypeArtifactId=scaffold \
-DarchetypeVersion=1.0 -X -DarchetypeCatalog=local \
```
