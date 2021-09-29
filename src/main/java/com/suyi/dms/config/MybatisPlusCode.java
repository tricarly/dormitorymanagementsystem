package com.suyi.dms.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class MybatisPlusCode {
    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
         AutoGenerator mpg = new AutoGenerator();    // 配置策略
    // 1、全局配置
         GlobalConfig gc = new GlobalConfig();
         String projectPath = System.getProperty("user.dir");
         gc.setOutputDir(projectPath+"/src/main/java");
         gc.setAuthor("suyi");
         gc.setOpen(false);
         gc.setFileOverride(true); // 是否覆盖
         gc.setServiceName("%sService"); // 去Service的I前缀
         gc.setIdType(IdType.AUTO);
         gc.setDateType(DateType.ONLY_DATE);
         mpg.setGlobalConfig(gc);
    //2、设置数据源
         DataSourceConfig dsc = new DataSourceConfig();
         dsc.setUrl("jdbc:mysql://localhost:3306/dms? useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
         dsc.setDriverName("com.mysql.cj.jdbc.Driver");
         dsc.setUsername("root");
         dsc.setPassword("123456");
         dsc.setDbType(DbType.MYSQL);
         mpg.setDataSource(dsc);
    //3、包的配置
         PackageConfig pc = new PackageConfig();
         pc.setModuleName("dms");
         pc.setParent("com.suyi");
         pc.setXml("mapper");
         pc.setEntity("pojo");
         pc.setMapper("dao");
         pc.setService("service");
         pc.setController("controller");
         mpg.setPackageInfo(pc);
    //4、策略配置
         StrategyConfig strategy = new StrategyConfig();
         strategy.setInclude("user","unbackform","student","record","dormitory","backform","application"); // 设置要映射的表名    strategy.setNaming(NamingStrategy.underline_to_camel);    strategy.setColumnNaming(NamingStrategy.underline_to_camel);    strategy.setEntityLombokModel(true); // 自动lombok；
         strategy.setNaming(NamingStrategy.underline_to_camel);
         strategy.setColumnNaming(NamingStrategy.underline_to_camel);
         strategy.setEntityLombokModel(true);
//         strategy.setLogicDeleteFieldName("deleted");    // 自动填充配置
         TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
         TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
         ArrayList<TableFill> tableFills = new ArrayList<>();
         tableFills.add(createTime);
         tableFills.add(updateTime);
         strategy.setTableFillList(tableFills);    // 乐观锁
//         strategy.setVersionFieldName("version");
         strategy.setRestControllerStyle(true);
         strategy.setControllerMappingHyphenStyle(true); // localhost:8080/hello_id_2
         mpg.setStrategy(strategy);
    mpg.execute(); //执行
           }
}
