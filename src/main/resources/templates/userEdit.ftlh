<#import "parts/common.ftlh" as c>

<@c.header />
<div class="mb-3"><h4>User editor</h4></div>
<form action="/user" method="post">
    <input class="text-dark mb-1" type="text" name="username" value="${user.username}">
    <#list roles as role>
    <div>
        <input class="dark-input mr-2" type="checkbox" name="${role}"
                ${user.roles?seq_contains(role)?string("checked", "")}>
        <label>${role}</label>
    </div>
    </#list>
    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <a href="/user">Go back</a>
    <button class="btn btn-primary ml-1" type="submit">Save</button>
</form>
<@c.footer />