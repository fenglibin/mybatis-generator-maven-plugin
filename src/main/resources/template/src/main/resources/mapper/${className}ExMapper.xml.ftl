<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameFirstLower = table.classNameFirstLower>
<#assign idColumnNameFirstLower = table.idColumn.columnName?uncap_first>
<#assign idColumnJavaType = table.idColumn.javaType>
<#macro mapperEl value>${r"#{"}${value},${"jdbcType="}<@jdbcType table.idColumn.sqlTypeName/>}</#macro>
<#macro mapperE2 value>${r"#{record."}${value},${"jdbcType="}<@jdbcType table.idColumn.sqlTypeName/>}</#macro>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basepackage}.${modelpackage}.mapper.${className}Mapper">

</mapper>
