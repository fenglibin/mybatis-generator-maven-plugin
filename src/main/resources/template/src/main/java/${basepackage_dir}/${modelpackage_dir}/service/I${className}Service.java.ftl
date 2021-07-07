<#include "/java_copyright.include"/>
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${modelpackage}.service;

import ${basepackage}.${modelpackage}.entity.${className};
import com.eeeffff.monitor.eklogmonitor.common.service.IService;
/**
* ${className}Mapper.
*
* @author  ${author}
* @version ${version}
* @date    ${now?string('yyyy-MM-dd hh:mm:ss')}
*/
public interface I${className}Service extends IService<${className}> {

}
