<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div>Add new user</div>
${message!""}
<@l.login "/registration" />
<@c.footer />