<#include "../macros/global.ftl">
<#include "common_header.ftl"/>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
        <@layoutSection header />
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-8"><@layoutSection main /></div>
        <div class="col-xs-6 col-md-4"><@layoutSection aside /></div>
    </div>
</div>

<footer class="footer">
    <div class="container">
    <@layoutSection footer />
    </div>
</footer>

<#include "common_footer.ftl" />