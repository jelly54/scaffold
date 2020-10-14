package ${groupId}.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    //要生成的表，及输出路径
    static String[] TO_CREATE_TABLES = new String[]{"demo"};
    static String BASE_PACKAGE = "${groupId}";

    static String BASE_OUT_PUT_DIR = "${rootArtifactId}-core/src/main/java";
    static String XML_OUT_PUT_DIR = "${rootArtifactId}-core/src/main/resources/mapper";
    static String API_OUT_PUT_DIR = "${rootArtifactId}-api/src/main/java/" + BASE_PACKAGE + "/api";

    //配置数据源
    static String DATA_SOURCE_URL = "jdbc:mysql://localhost:3306/test?tinyInt1isBit=false&useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8";
    static String DATA_SOURCE_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DATA_SOURCE_USER = "root";
    static String DATA_SOURCE_PASSWORD = "123456";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        initGlobalConfig(mpg);
        initDataSourceConfig(mpg);
        initPackageConfig(mpg);

        customConfig(mpg);
        customTemplateConfig(mpg);

        initStrategyConfig(mpg);

        mpg.execute();
    }

    /**
     * 策略配置
     */
    private static void initStrategyConfig(AutoGenerator mpg) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(TO_CREATE_TABLES);

        strategy.setEntityBuilderModel(true);
        strategy.setEntityLombokModel(true);

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        // 去掉字段前缀
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
    }

    /**
     * 自定义模板配置
     */
    private static void customTemplateConfig(AutoGenerator mpg) {
        TemplateConfig tc = new TemplateConfig()
                .setXml(null)
                .setEntity("/templates/entity.java")
                .setMapper("/templates/dao.java")
                .setService(null)
                .setServiceImpl("/templates/serviceImpl.java")
                .setController("/templates/controller.java");
        mpg.setTemplate(tc);
    }

    /**
     * 自定义注入配置
     */
    private static void customConfig(AutoGenerator mpg) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return XML_OUT_PUT_DIR + "/" + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("templates/service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return API_OUT_PUT_DIR + "/" + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    }

    /**
     * 包配置
     */
    private static void initPackageConfig(AutoGenerator mpg) {
        PackageConfig pc = new PackageConfig();
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(BASE_PACKAGE);
        pc.setEntity("entity");
        pc.setController("controller");
        pc.setMapper("dao");
        pc.setXml("mapper");
        pc.setService("api");
        pc.setServiceImpl("service");
        // 表名称前需要删除的前缀
//        pc.setModuleName("t_gmmp");
        mpg.setPackageInfo(pc);
    }

    /**
     * 数据源配置
     */
    private static void initDataSourceConfig(AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DATA_SOURCE_URL);
        dsc.setDriverName(DATA_SOURCE_DRIVER);
        dsc.setUsername(DATA_SOURCE_USER);
        dsc.setPassword(DATA_SOURCE_PASSWORD);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                //tinyint转换成Integer
                if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.INTEGER;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);
    }

    /**
     * 全局配置
     */
    private static void initGlobalConfig(AutoGenerator mpg) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(BASE_OUT_PUT_DIR);
        // 是否覆盖文件
        gc.setFileOverride(false);
        //开启 activeRecord
        gc.setActiveRecord(false);
        // xml 二级缓存
        gc.setEnableCache(false);
        // xml resultMap
        gc.setBaseResultMap(true);
        // xml columList
        gc.setBaseColumnList(true);
        // 否打开输出目录
        gc.setOpen(false);
        gc.setEntityName("%sEntity");
        gc.setXmlName("%sMapper");
        gc.setMapperName("%sDao");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setAuthor("zhang guo dong");
        gc.setSwagger2(true);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);
    }

}