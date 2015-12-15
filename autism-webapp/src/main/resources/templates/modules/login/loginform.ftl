<div class="loginform">
    <#if parameterMap.failure??>
        <div class="alert alert-danger">
            Failed to login with credentials provided.
        </div>
    </#if>
    <form class="form-signin" action="/module/login" method="POST">
        <input type="hidden" name="successUrl" value="/" />
        <input type="hidden" name="failureUrl" value="/login?failure=true" />
        <input type="hidden" name="accountUrl" value="/create-patient" />
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" name="email" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" class="form-control" placeholder="Password" required>

        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>