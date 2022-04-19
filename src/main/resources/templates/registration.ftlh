<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="mb-4"><h3>Add new user</h3></div>
<div class="mb-3 text-danger"><h5>${message!}</h5></div>
<@l.login "/registration" true/>
<@c.footer />