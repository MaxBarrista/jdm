<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="mb-5"><h4>Add new user</h4></div>
${message!}
<@l.login "/registration" true/>
<@c.footer />