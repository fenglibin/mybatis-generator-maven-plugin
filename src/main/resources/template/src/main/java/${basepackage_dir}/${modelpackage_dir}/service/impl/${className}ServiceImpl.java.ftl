<#include "/java_copyright.include"/>
<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${modelpackage}.service.impl;

import ${basepackage}.${modelpackage}.entity.${className};
import ${basepackage}.${modelpackage}.mapper.${className}Mapper;
import ${basepackage}.${modelpackage}.service.I${className}Service;
import com.eeeffff.monitor.eklogmonitor.common.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
* ${className}Mapper.
*
* @author  ${author}
* @version ${version}
* @date    ${now?string('yyyy-MM-dd hh:mm:ss')}
*/
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements I${className}Service {


}
