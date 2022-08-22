<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="mb-5"><h4>Log in</h4></div>
<div class="mb-3 text-success">${message!}</div>
<div class="mb-3 text-danger">${errorMessage!}</div>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<@l.login "/login" false/>
<@c.footer />