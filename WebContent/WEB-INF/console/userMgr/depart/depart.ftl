<#if dlist?size gt 0>,
<#list dlist as depart>
<#if depart.id!=parentId>
{
    "id" : "${depart.id!}",
    "text": "${depart.name!}"
    <#if depart.child?size gt 0>,
    "expanded": "true",
	"children": [
		<#assign dlist=depart.child>
		<#include "depart.ftl">
	]
	</#if>
}<#if depart_has_next>,</#if>
</#list>
</#if>
</#if>