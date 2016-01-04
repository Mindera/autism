<create-patient-form>
<form class="form-horizontal" role="form" method="post" action="">
    <h2>Create patient</h2>
    <#if form??>
        <div class="alert alert-danger">
            There was an error creating the patient.
        </div>
    </#if>
    <div class="form-group">
        <label for="name" class="col-sm-3 control-label">Name</label>
        <div class="col-sm-9">
            <input type="text" name="description" placeholder="Full Name" class="form-control" autofocus>
            <span class="help-block">Complete name</span>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">
            <button type="submit" class="btn btn-primary btn-block">Create</button>
        </div>
    </div>
</form>
</create-patient-form>