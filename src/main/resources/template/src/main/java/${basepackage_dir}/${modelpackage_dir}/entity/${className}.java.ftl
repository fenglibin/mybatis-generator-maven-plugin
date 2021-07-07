<#include "/java_copyright.include"/>
<#include "/macro.include"/>
<#assign impData = "0">
<#assign impBigDecimal = "0">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${modelpackage}.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
<#list table.columns as column>
<#if column.javaType == "BigDecimal" && impBigDecimal=="0">
<#assign impBigDecimal = "1">
import java.math.BigDecimal;
</#if>
<#if column.javaType == "java.time.LocalDateTime" && impData=="0">
<#assign impData = "1">
import java.time.LocalDateTime;
</#if>
</#list>
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ${table.sqlName}.
 *
 * @author  ${author}
 * @version ${version}
 * @date    ${now?string('yyyy-MM-dd hh:mm:ss')}
 */
@TableName("${table.sqlName}")
public class ${className} implements Serializable {

  private static final long serialVersionUID = 1L;

	<#list table.columns as column>
  /**
   * <#if column.remarks??  && column.remarks != "">${column.remarks}<#else>${column.columnNameLower}</#if>.
   */
    <#if column.pk>@TableId(value ="`${column.sqlName}`",type = IdType.AUTO)<#else>@TableField("`${column.sqlName}`")</#if>
    private <@mapperFQN column.javaType/> ${column.columnNameLower};

	</#list>

	<#list table.columns as column>
  /**
   * 设置<#if column.remarks??  && column.remarks != "">${column.remarks}<#else>${column.columnNameLower}</#if>.
   *
   * @param ${column.columnNameLower} <#if column.remarks??  && column.remarks != "">${column.remarks}<#else>${column.columnNameLower}</#if>
   */
  public void set${column.columnNameLower?cap_first}(<@mapperFQN column.javaType/> ${column.columnNameLower}) {
    this.${column.columnNameLower} = ${column.columnNameLower};
  }

  /**
   * 获取<#if column.remarks??  && column.remarks != "">${column.remarks}<#else>${column.columnNameLower}</#if>.
   *
   * @return ${column.columnNameLower}  <#if column.remarks??  && column.remarks != "">${column.remarks}<#else>${column.columnNameLower}</#if>
   */
  public <@mapperFQN column.javaType/> get${column.columnNameLower?cap_first}() {
    return this.${column.columnNameLower};
  }

</#list>
  @Override
  public String toString() {
    return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
<#list table.columns as column>
        .append("${column.columnNameLower}",get${column.columnNameLower?cap_first}())
</#list>
        .toString();
  }
}

