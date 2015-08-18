"children":[
	<#list clist as channel>
		{
		"id": "${channel.id}",
		"name": "${channel.name!}",
		<#if chnlIds??&&chnlIds?seq_contains(channel.id)>
		"checked":true,
		</#if>
		<#if channel.child?size gt 0>
		<#assign clist=channel.child>
		"isParent": true,
		"open": false,
		<#include "chnl.ftl">
		<#else>
		"isParent": false
		</#if>
		}<#if channel_has_next>,</#if>
	</#list>
]