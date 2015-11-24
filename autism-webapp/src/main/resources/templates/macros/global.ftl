<#macro layoutSection section>
    <#if section??>
        <#list section as module>
            <#if module??>
                <#if  module.status?? && module.status != "FAILURE_TO_LOAD">
                ${module.response}
                </#if>
            </#if>
        </#list>
    </#if>
</#macro>
