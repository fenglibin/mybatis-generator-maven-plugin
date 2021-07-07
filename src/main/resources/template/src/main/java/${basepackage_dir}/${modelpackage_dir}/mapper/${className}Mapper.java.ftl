<#include "/java_copyright.include"/>
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${modelpackage}.mapper;

import ${basepackage}.${modelpackage}.entity.${className};
import org.apache.ibatis.annotations.Mapper;
import com.eeeffff.monitor.eklogmonitor.common.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
/**
* ${className}Mapper.
*
* @author  ${author}
* @version ${version}
* @date    ${now?string('yyyy-MM-dd hh:mm:ss')}
*/
@Mapper
@Repository
public interface ${className}Mapper extends BaseMapper<${className}> {

}
