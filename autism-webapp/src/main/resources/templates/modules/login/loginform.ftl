<h3>
    Please in with your credentials
</h3>

<#if parameterMap.failure??>
<div class="alert alert-danger">
    Failed to login with credentials provided.
</div>
</#if>

<div class="well loginform">

    <form class="form-horizontal" action="/module/login" method="POST">
        <input type="hidden" name="successUrl" value="/" />
        <input type="hidden" name="failureUrl" value="/login?failure=true" />
        <input type="hidden" name="accountUrl" value="/create-patient" />
        <fieldset>
            <div class="form-group">
                <label for="inputEmail" class="col-lg-2 control-label">Email</label>
                <div class="col-lg-10">
                    <input type="text" class="form-control" id="inputEmail" name="email" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-lg-2 control-label">Password</label>
                <div class="col-lg-10">
                    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Remember me
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>