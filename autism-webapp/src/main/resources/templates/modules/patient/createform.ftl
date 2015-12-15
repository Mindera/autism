<form class="form-horizontal" role="form" method="post" action="">
    <input type="hidden" name="successUrl" value="/" />
    <h2>Create patient</h2>
    <div class="form-group">
        <label for="name" class="col-sm-3 control-label">Name</label>
        <div class="col-sm-9">
            <input type="text" name="name" placeholder="Full Name" class="form-control" autofocus>
            <span class="help-block">Complete name</span>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">
            <button type="submit" class="btn btn-primary btn-block">Create</button>
        </div>
    </div>
</form>