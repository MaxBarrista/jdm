<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="mb-5"><h4>Log in</h4></div>
<div class="mb-3 text-success">${message!}</div>
<div class="mb-3 text-danger">${errorMessage!}</div>
<@l.login "/login" false/>
<@c.footer />