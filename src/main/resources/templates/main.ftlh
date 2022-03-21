<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div>
    <@l.logout /><br>
    <span><a href="/user">List of users</a></span>
</div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="manufacturer" placeholder="Марка:">
        <input type="text" name="model" placeholder="Модель:">
        <input type="text" name="modelYear" placeholder="Год:">
        <input type="text" name="mileage" placeholder="Пробег:">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
<div>Список автомобилей:</div>
<form method="get" action="/main">
    <input type="text" name="carModel" placeholder="Фильтр(Модель)" value="${carModel!}">
    <button type="submit">Найти</button>
</form>
<table>
    <tr class="table-header">
        <td><b>ID</b></td>
        <td><span>Марка</span></td>
        <td><span>Модель</span></td>
        <td><span>Год</span></td>
        <td><span>Пробег</span></td>
        <td><strong>Владелец</strong></td>
        <td><span>Изображение</span></td>
    </tr>
    <#list cars as car>
    <tr>
        <td><b>${car.id}</b></td>
        <td><span>${car.manufacturer}</span></td>
        <td><span>${car.model}</span></td>
        <td><span>${car.modelYear}</span></td>
        <td><span>${car.mileage}</span></td>
        <td><strong>${car.ownerName}</strong></td>
        <td>
            <#if car.filename??>
                <img src="/img/${car.filename}" alt="${car.model}">
            </#if>
        </td>
    </tr>
    <#else>
    No cars yet
    </#list>
</table>

<@c.footer/>