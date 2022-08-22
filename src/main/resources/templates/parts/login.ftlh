<#include "security.ftlh">

<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control <#if usernameError??>is-invalid</#if>"
                       placeholder="username" value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control <#if emailError??>is-invalid</#if>"
                           placeholder="example@gmail.com" value="<#if user??>${user.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" placeholder="password"
                       class="form-control <#if passwordError?? || confirmPasswordError??>is-invalid</#if>"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row mb-5">
                <label class="col-sm-2 col-form-label">Confirm password:</label>
                <div class="col-sm-6">
                    <input type="password" name="confirmPassword" placeholder="confirm password"
                           class="form-control <#if confirmPasswordError??>is-invalid</#if>"/>
                    <#if confirmPasswordError??>
                        <div class="invalid-feedback">
                            ${confirmPasswordError}
                        </div>
                    </#if>
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