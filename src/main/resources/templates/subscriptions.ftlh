<#import "parts/common.ftlh" as c>

<@c.header />
<h3>${userChannel.username}</h3>
<div>${type}</div>
<ul class="list-group bg-secondary">
    <#list users as user>
        <li class="list-group-item bg-secondary">
            <h5>
                <a href="/user-cars/${user.id}">${user.getUsername()}</a>
            </h5>
        </li>
    </#list>
</ul>
<@c.footer />