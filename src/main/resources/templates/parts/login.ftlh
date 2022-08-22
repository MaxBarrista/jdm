<#include "security.ftlh">

<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" required placeholder="username"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control " required placeholder="example@gmail.com"/>
                </div>
            </div>
        </#if>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" required placeholder="password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row mb-5">
                <label class="col-sm-2 col-form-label">Confirm password:</label>
                <div class="col-sm-6">
                    <input type="password" name="confirmPassword" required placeholder="confirm password"
                           class="form-control <#if confirmPasswordError??>is-invalid</#if>"/>
                </div>
            </div>
        </#if>
        <#if isRegisterForm>
            <div class="form-group row">
                <button class="btn btn-primary" type="submit" >Sign Up</button>
            </div>
            <div class="form-group row">
                <span class="mr-1">Already registered?</span><a href="/login">Log in</a>
            </div>
        <#else>
            <div class="form-group row">
                <button class="btn btn-primary" type="submit" >Sign In</button>
            </div>
            <div class="form-group row">
                <span class="mr-1">Have no account yet?</span><a href="/registration">Sign Up</a>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>

<#macro logoutButton>
    <form action="/logout" method="post">
        <button class="btn btn-primary" type="submit" >Sign Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>

<#macro loginButton>
    <form action="/login" method="get">
        <button class="btn btn-primary" type="submit" >Log in</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>