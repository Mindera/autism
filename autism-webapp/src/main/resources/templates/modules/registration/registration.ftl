<form class="form-horizontal" role="form" method="post" action="">
    <h2>Registration Form</h2>
    <div class="form-group">
        <label for="firstName" class="col-sm-3 control-label">Name</label>
        <div class="col-sm-9">
            <input type="text" name="name" placeholder="Full Name" class="form-control" autofocus>
            <span class="help-block">First and Last name, eg.: Joe Santos</span>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="col-sm-3 control-label">Email</label>
        <div class="col-sm-9">
            <input type="email" name="email" placeholder="Email" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="col-sm-3 control-label">Password</label>
        <div class="col-sm-9">
            <input type="password" name="password" placeholder="Password" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">
            <button type="submit" class="btn btn-primary btn-block">Register</button>
        </div>
    </div>
</form>