**基于MySQL数据库表的代码生成器**

可以生成Mapper、Service及ServiceImpl、Mapper.xml等代码。

项目集成Maven插件引入，pom.xml的插件增加如下插件：

```xml
		<plugin>
			<groupId>com.eeeffff</groupId>
			<artifactId>mybatis-generator-maven-plugin</artifactId>
			<version>1.0.5</version>
			<configuration>
				<tableName>
                    <!-- 指定需要生成的表名-->
					app_monitor_config,app_monitor_data,app_monitor_dept
				</tableName>
                <!-- 
               生成文件的输出工程，该参数不是必須，默认的生成文件的输出工程为当前执行生成的工程，
               如果指定了输出工程，则该工程必须是和当前工程在相同的文件目录，即相同的父文件目录，否则会报错
               -->
                <!--outProject>eklogmonitor-dao</outProject-->
                <!--指定需要移除的表的前缀，如果需要同时指定多，以英文的逗号分隔-->
				<tableRemovePrefixes>test</tableRemovePrefixes>
				<packageRemoves>service</packageRemoves>
                <!-- 指定生成时使用的数据源，该数据源配置到application.yml或application.properties中 -->
				<dataSourcePath>spring.datasource.dynamic.datasource.master</dataSourcePath>
			</configuration>
		</plugin>
```

pom.xml中的dependency信赖：

```xml
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
	</dependency>
	<!-- sql日志打印 -->
	<dependency>
		<groupId>p6spy</groupId>
		<artifactId>p6spy</artifactId>
		<version>3.8.0</version>
	</dependency>
	<dependency>
		<groupId>org.hibernate.validator</groupId>
		<artifactId>hibernate-validator</artifactId>
		<version>6.0.16.Final</version>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-jdbc</artifactId>
	</dependency>
```
application.yml中增加数据源的配置：

```yaml
spring:
  redis:
    database: 0
    host: 127.0.0.1
    password: 
    port: 6379
    timeout: 2000
  aop:
    auto: true
    proxy-target-class: true
  datasource:
    dynamic:
      datasource:
        master:
          username: root
          password: root
          url: jdbc:p6spy:mysql://127.0.0.1:3306/erp_metrics?useAffectedRows=true&useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai
          driver-class-name: com.p6spy.engine.spy.P6SpyDriver
          type: com.zaxxer.hikari.HikariDataSource
          initialSize: 5
          minIdle: 5
          maxActive: 20
```

代码生成执行命令：

```shell
mvn mybatis-generator:generator
```

