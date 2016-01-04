<#macro layoutSection section>
    <#if section??>
        <#list section as module>
            <#if module??>
                <module class="${module.module.module}">
                <#if  module.status?? && module.status == "SUCCESS">
                    ${module.response}
                <#else>
                    Failure to load module: ${module.module.module}
                </#if>
                </module>
            </#if>
        </#list>
    </#if>
</#macro>
