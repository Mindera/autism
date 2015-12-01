<#macro layoutSection section>
    <#if section??>
        <#list section as module>
            <#if module??>
                <module class="${module.module.module}">
                <#if  module.status?? && module.status != "FAILURE_TO_LOAD">
                ${module.response}
                </#if>
                </module>
            </#if>
        </#list>
    </#if>
</#macro>
