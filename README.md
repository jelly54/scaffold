利用 `maven` 的 `archetype` 制作的 `spring boot` 项目脚手架

# 安装archetype

```shell script
mvn clean install
```

# 生成新的脚手架

```shell script
mvn archetype:generate \
-DgroupId=com.h2t.test \
-DartifactId=superman-demo \
-Dversion=1.0.0-SNAPSHOT \
-DarchetypeGroupId=com.jelly \
-DarchetypeArtifactId=scaffold \
-DarchetypeVersion=1.0 -X -DarchetypeCatalog=local \
```
