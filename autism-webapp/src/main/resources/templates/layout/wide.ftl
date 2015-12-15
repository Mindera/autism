<#include "common_header.ftl"/>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
        <@layoutSection header />
        </div>
    </div>
</nav>

<div class="container">
    <@layoutSection main />
</div>

<footer class="footer">
    <div class="container">
    <@layoutSection footer />
    </div>
</footer>

<#include "common_footer.ftl" />