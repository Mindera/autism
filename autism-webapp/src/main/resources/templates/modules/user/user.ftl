<ul class="nav navbar-nav navbar-right">
<#if user??>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
           aria-expanded="false">${user.account.description} <span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="/patient/profile"><i class="glyphicon glyphicon-plus-sign"></i> Edit profile</a></li>
            <li><a href="/patient/profile"><i class="glyphicon glyphicon-plus-sign"></i> Manage accesses</a></li>

            <#if patientList??>
                <li role="separator" class="divider"></li>
                <#list patientList as patient>
                    <li>
                        <a href="/module/patient/switch/${patient.id}"><i class="glyphicon glyphicon-plus-sign"></i>
                        ${patient.description}
                        </a>
                    </li>
                </#list>
            </#if>

            <li role="separator" class="divider"></li>
            <li><a href="/create-patient"><i class="glyphicon glyphicon-plus-sign"></i> Add patient</a></li>

        </ul>
    </li>
<#else>
    <li><a href="/login">Login</a></li>
    <li><a href="/registration">Create account</a></li>
</#if>

</ul>
