<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.header />
<h5>${username}</h5>
<div class="mb-3 text-success">${message!}</div>
<div class="mb-3 text-danger">${errorMessage!}</div>
<form method="post">
    <#--    Email-->
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" required placeholder="example@gmail.com"
                   value="${email!}"/>
        </div>
    </div>
    <#--    Change password-->
    <a class="btn btn-secondary p-1 mb-3" data-toggle="collapse" href="#changePassword" role="button" aria-expanded="false"
       aria-controls="changePassword">
        Change password
    </a>
    <div class="collapse" id="changePassword">
        <div class="form-group mt-3">
            <div class="form-group row">
                <div class="col-sm-6">
                    <input type="password" name="oldPassword" class="form-control" placeholder="Old password"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-6">
                    <input type="password" name="newPassword" class="form-control" placeholder="New password"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-6">
                    <input type="password" name="newPasswordConfirm" class="form-control"
                           placeholder="Confirm new password"/>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group row">
        <button class="btn btn-primary" type="submit" >Save</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
<@c.footer />