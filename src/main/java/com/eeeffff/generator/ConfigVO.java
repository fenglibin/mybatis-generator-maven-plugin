package com.eeeffff.generator;

/**
 * 类或方法的功能描述 :TODO
 *
 * @author: fenglibin
 */
public class ConfigVO {
    private String applicationName;
    private String jdbcUrl;
    private String jdbcDriver;
    private String jdbcUsername;
    private String jdbcPassword;
    private String outProject;
    private String outRoot;
    private String templateRootDir;
    private String tableRemovePrefixes;
    private String basepackage;
    private String modelpackage;

    public String getBasepackage() {
        return basepackage;
    }

    public void setBasepackage(String basepackage) {
        this.basepackage = basepackage;
    }

    public String getModelpackage() {
        return modelpackage;
    }

    public void setModelpackage(String modelpackage) {
        this.modelpackage = modelpackage;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getOutRoot() {
        return outRoot;
    }

    public void setOutRoot(String outRoot) {
        this.outRoot = outRoot;
    }

    public String getTemplateRootDir() {
        return templateRootDir;
    }

    public void setTemplateRootDir(String templateRootDir) {
        this.templateRootDir = templateRootDir;
    }

    public String getTableRemovePrefixes() {
        return tableRemovePrefixes;
    }

    public void setTableRemovePrefixes(String tableRemovePrefixes) {
        this.tableRemovePrefixes = tableRemovePrefixes;
    }

	public String getOutProject() {
		return outProject;
	}

	public void setOutProject(String outProject) {
		this.outProject = outProject;
	}
}

