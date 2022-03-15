<#import "parts/common.ftlh" as c>

<@c.header />
    User editor
<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}">
    <#list roles as role>
    <div>
        <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}><label>${role}</label>
    </div>
    </#list>
    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Save</button>
</form>
<@c.footer />