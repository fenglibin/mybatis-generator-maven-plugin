package com.eeeffff.generator;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **
 * @goal generator
 * @requiresProject true
 * @phase process-spurce
 *
 *  @Mojo( name = "generator", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
 */

import com.google.common.base.Strings;
import com.suzy.framework.mybatis.generator.GeneratorFacade;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 
 * @author fenglibin
 *
 */
@SuppressWarnings("restriction")
@Mojo( name = "generator", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyBatisGeneratorMojo extends AbstractMojo {


    /**
     * 当前执行代码生成的项目跟目录.
     */
    @Parameter(defaultValue = "${project.basedir}" ,required = true)
    private File basedir;
    
    /**
     * 生成文件输出的项目名称，该项目所在文件目录必须与执行代码生成工程在相同目录下
     */
    @Parameter(defaultValue = "${outProject}" ,required = false)
    private String outProject;
    
    /**
     * 生成文件输出的文件目录.
     */
    private File outProjectBaseDir;

    /**
     * 项目资源目录
     */
    @Parameter(defaultValue ="${project.build.sourceDirectory}",required = true)
    private File sourceDirectory;


    /**
     * 指定datasource读取路径.
     */
    @Parameter(defaultValue = "${dataSourcePath}", required = false)
    private String dataSourcePath;

    /**
     * 要生成的表名.
     */
    @Parameter(defaultValue = "${tableName}", required = true)
    private String tableName;

    /**
     * 移除表的前缀，多个逗号分割.
     */
    @Parameter(defaultValue = "${tableRemovePrefixes:}")
    private String tableRemovePrefixes;


    private YamlUtil yamlUtil;

    private String fileSeparator = java.io.File.separator;

    private static final String genPrefix = "gen.";

    private static  String dsPrefix = "spring.datasource.";

    private static String basepackage = "";

    private static String modelpackage = "";

    private static String templateFolder ="";




    private ConfigVO configVO = new ConfigVO();


    @Override
    public void execute() throws MojoExecutionException {
    	getLog().info("Default run code generator project base dir:"+basedir);
    	if(outProject!=null && outProject.trim().length()>0) {
    		String ourProjectPath = basedir.getParentFile().getAbsolutePath()+fileSeparator+outProject;
    		getLog().info("The outProject is setted, the code will generated to :"+ourProjectPath);
    		outProjectBaseDir = new File(ourProjectPath);
    		if(!outProjectBaseDir.exists()) {
    			throw new MojoExecutionException("代码生成文件输出的项目所在文件目录，必须与执行代码生成工程的文件路径："+basedir.getAbsolutePath()+"，在相同父目录下，即："+basedir.getParentFile().getAbsolutePath()+"　下面");
    		}
    	}else {
    		outProjectBaseDir = basedir;
    	}
    	getLog().info("Output project base dir:"+basedir);
        getLog().info("gen table:" + tableName);

        try {
            initEnv();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        getLog().info("basepackage:" + configVO.getBasepackage());
        getLog().info("modelpackage:" + configVO.getModelpackage());
        getLog().info("outRoot:" + configVO.getOutRoot());

        System.setProperty("useSystemProperty", "true");
        System.setProperty(genPrefix + "skipEndsWith", "Mapper.java,Service.java,ServiceImpl.java,ExMapper.xml");
        System.setProperty(genPrefix + "jdbc.url", configVO.getJdbcUrl());
        System.setProperty(genPrefix + "jdbc.driver", configVO.getJdbcDriver());
        System.setProperty(genPrefix + "jdbc.username", configVO.getJdbcUsername());
        System.setProperty(genPrefix + "jdbc.password", configVO.getJdbcPassword());
        System.setProperty(genPrefix + "override", "true");
        System.setProperty(genPrefix + "basepackage", configVO.getBasepackage());
        System.setProperty(genPrefix + "modelpackage", configVO.getModelpackage());
        System.setProperty(genPrefix + "outRoot", configVO.getOutRoot());
        System.setProperty(genPrefix + "templateRootDir", configVO.getTemplateRootDir());
        System.setProperty(genPrefix + "tableRemovePrefixes", configVO.getTableRemovePrefixes());
        System.setProperty(genPrefix + "java_typemapping.java.sql.Timestamp", "java.time.LocalDateTime");
        System.setProperty(genPrefix + "java_typemapping.java.sql.Date", "java.time.LocalDateTime");
        System.setProperty(genPrefix + "java_typemapping.java.sql.Time", "java.time.LocalDateTime");
        System.setProperty(genPrefix + "java_typemapping.java.lang.Short", "Short");
        System.setProperty(genPrefix + "java_typemapping.java.lang.Smallint", "Short");
        System.setProperty(genPrefix + "java_typemapping.java.lang.Byte", "Integer");
        System.setProperty(genPrefix + "java_typemapping.java.math.BigDecimal", "BigDecimal");
        System.setProperty(genPrefix + "java_typemapping.java.sql.Clob", "String");
        System.setProperty(genPrefix + "java_typemapping.java.sql.MEDIUMINT", "Integer");



        GeneratorFacade facade = new GeneratorFacade();


        try {
          List<String> tableNames =  Arrays.asList(tableName.split(","));
            getLog().info(" gen tables" +tableName);
            for (String name : tableNames) {
                facade.generateByTable(name, configVO.getTemplateRootDir());
            }

        } catch (Exception e) {
            getLog().error(e);
        }


    }

    public void initEnv() throws Exception {
    	
        if(Strings.isNullOrEmpty(tableName)){
            getLog().error(" name 参数不能为空!");
            return;
        }
        if(!Strings.isNullOrEmpty(dataSourcePath)){
            if(dataSourcePath.endsWith(".")){
                dsPrefix=dataSourcePath;
            }else {
                dsPrefix=dataSourcePath+".";
            }

        }


        String resourcesPath = basedir.toString() + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources";

        templateFolder = basedir.toString()+fileSeparator+"target"+fileSeparator+"mybatis-generator-maven-plugin"+fileSeparator;

        File file=new File(templateFolder);
        if(!file.exists() && !file.isDirectory()){
            String jarTemplatePath = MyBatisGeneratorMojo.class.getClassLoader().getResources("template").nextElement().getPath();
            loadRecourseFromJarByFolder(jarTemplatePath);
        }
        String applicationFile = resourcesPath + fileSeparator + "application.yml";

        if (new File(resourcesPath + fileSeparator + "application.yml").exists()) {
            applicationFile = resourcesPath + fileSeparator + "application.yml";
        } else if (new File(resourcesPath + fileSeparator + "application.yaml").exists()) {
            applicationFile = resourcesPath + fileSeparator + "application.yaml";
        } else {
            throw new Exception("application文件不存在!");
        }


        //yamlUtil = new YamlUtil(bootStarpFile);

        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        Model pomModel = pomReader.read(new FileReader(outProjectBaseDir.toString() + File.separator + "pom.xml"));

        String groupId  = pomModel.getGroupId();
        if(groupId==null){
            groupId=  pomModel.getParent().getGroupId();
        }
        String artifactId =  pomModel.getArtifactId().replace("-", ".");

        String lastGroupName = groupId.substring(groupId.lastIndexOf(".")+1);
        String startArtifactName = artifactId.substring(0,groupId.indexOf("."));

        if(lastGroupName.endsWith(startArtifactName)){
            modelpackage = artifactId.substring(startArtifactName.length()+1);
        }else {
            modelpackage = artifactId;
        }

        basepackage = groupId;

        //configVO.setApplicationName(yamlUtil.getKey("spring.application.name"));
        configVO.setBasepackage(basepackage);
        configVO.setModelpackage(modelpackage);

        yamlUtil = new YamlUtil(applicationFile);


        if(Strings.isNullOrEmpty(yamlUtil.getKey(dsPrefix+"url"))){
            throw new Exception(dsPrefix+"url 配置不能为空!");
        }

        if(Strings.isNullOrEmpty(yamlUtil.getKey(dsPrefix+"username"))){
            throw new Exception(dsPrefix+"username 配置不能为空!");
        }

        if(Strings.isNullOrEmpty(yamlUtil.getKey(dsPrefix+"password"))){
            throw new Exception(dsPrefix+"password 配置不能为空!");
        }

        configVO.setJdbcDriver("com.mysql.jdbc.Driver");
        configVO.setJdbcUrl(yamlUtil.getKey(dsPrefix+"url").replace(":p6spy",""));
        configVO.setJdbcUsername(yamlUtil.getKey(dsPrefix+"username"));
        configVO.setJdbcPassword(yamlUtil.getKey(dsPrefix+"password"));
        configVO.setOutRoot(outProjectBaseDir.toString());
        configVO.setTableRemovePrefixes(tableRemovePrefixes==null?"":tableRemovePrefixes);
        configVO.setTemplateRootDir(templateFolder+fileSeparator+"template");


    }


    /**
     * 当前运行环境资源文件是在jar里面的
     *
     * @param jarURLConnection
     * @throws IOException
     */
    private void copyJarResources(JarURLConnection jarURLConnection) throws IOException {
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            JarEntry entry = entrys.nextElement();
            if (entry.getName().startsWith(jarURLConnection.getEntryName()) && !entry.getName().endsWith("/")) {
                loadRecourseFromJar("/" + entry.getName());
            }
        }
        jarFile.close();
    }

	private void loadRecourseFromJarByFolder(String folderPath) throws IOException {

        URL url = new URL("jar:" + folderPath);
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof FileURLConnection) {
            copyFileResources(url, folderPath);
        } else if (urlConnection instanceof JarURLConnection) {
            copyJarResources((JarURLConnection) urlConnection);
        }

    }


    public void loadRecourseFromJar(String path) throws IOException {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }

        if(path.endsWith("/")){
            throw new IllegalArgumentException("The path has to be absolute (cat not end with '/').");
        }

        int index = path.lastIndexOf('/');

        String filename = path.substring(index + 1);
        String folderPath = templateFolder + path.substring(0, index + 1);

        // If the folder does not exist yet, it will be created. If the folder
        // exists already, it will be ignored
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // If the file does not exist yet, it will be created. If the file
        // exists already, it will be ignored
        filename = folderPath + filename;
        File file = new File(filename);

        if (!file.exists() && !file.createNewFile()) {
            //log.error("create file :{} failed", filename);
            return;
        }

        // Prepare buffer for data copying
        byte[] buffer = new byte[1024];
        int readBytes;

        // Open and check input stream
        URL url = getClass().getResource(path);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();

        if (is == null) {
            throw new FileNotFoundException("File " + path + " was not found inside JAR.");
        }

        // Open output stream and copy data between source file in JAR and the
        // temporary file
        OutputStream os = new FileOutputStream(file);
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } finally {
            // If read/write fails, close streams safely before throwing an
            // exception
            os.close();
            is.close();
        }

    }


    /**
     * 当前运行环境资源文件是在文件里面的
     * @param url
     * @param folderPath
     * @throws IOException
     */
    private void copyFileResources(URL url,String folderPath) throws IOException{
        File root = new File(url.getPath());
        if(root.isDirectory()){
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    loadRecourseFromJarByFolder(folderPath+"/"+file.getName());
                } else {
                    loadRecourseFromJar(folderPath+"/"+file.getName());
                }
            }
        }
    }

}
