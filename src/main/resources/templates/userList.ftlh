<#import "parts/common.ftlh" as c>

<@c.header />
List of users

<table>
    <thead>
    <tr>
        <td>Name</td>
        <td>Role</td>
        <td>is active?</td>
        <td><span>Actions</span></td>
    </tr>
    <tbody>
<#list users as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td><#if user.isActive()>yes<#else>no</#if></td>
        <td>
            <a href="/user/${user.id}"><img class="icon" src="/static/icons/editIcon.png"></a>
            <a href="/user/delete/${user.id}"><img class="icon" src="/static/icons/deleteIcon.png"></a>
        </td>
    </tr>
</#list>
    </tbody>
    </thead>
</table>
<@c.footer />