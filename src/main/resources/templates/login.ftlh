<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="mb-5"><h4>Log in</h4></div>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<#if errorMessage??>
    <div class="alert alert-danger" role="alert">
        ${errorMessage}
    </div>
</#if>
<#if message??>
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
</#if>
<@l.login "/login" false/>
<@c.footer />