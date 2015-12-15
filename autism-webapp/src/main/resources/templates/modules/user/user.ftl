<div class="user">
    <#if user??>
        Welcome!
    <#else>
        <ul>
            <li><a href="/login">Login</a></li>
            <li><a href="/registration">Create account</a></li>
        </ul>
    </#if>
</div>