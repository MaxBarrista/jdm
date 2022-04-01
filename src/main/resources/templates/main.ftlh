<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.header />
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="modelFilter" placeholder="Model" value="${modelFilter!}">
            <input type="text" name="manufacturerFilter" placeholder="Manufacturer" value="${manufacturerFilter!}"
                   class="ml-1">
            <button type="submit" class="btn btn-primary ml-1">Search</button>
        </form>
    </div>
</div>

<a class="btn btn-primary mb-3" data-toggle="collapse" href="#addNewCar" role="button" aria-expanded="false"
   aria-controls="addNewCar">
    Add new car
</a>
<div class="collapse" id="addNewCar">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control" name="manufacturer" placeholder="Manufacturer:">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="model" placeholder="Model:">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="modelYear" placeholder="Year:">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="mileage" placeholder="Mileage:">
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Save</button>
            </div>
        </form>
    </div>
</div>

<table>
    <tr class="table-header">
        <td><span>Id</span></td>
        <td><span>Image</span></td>
        <td><span>Manufacturer</span></td>
        <td><span>Model</span></td>
        <td><span>Year</span></td>
        <td><span>Mileage</span></td>
        <td><strong>Owner</strong></td>
        <td><span>Actions</span></td>
    </tr>
    <#list cars as car>
        <tr>
            <td><span>${car.id}</span></td>
            <td class="car-image">
                <#if car.filename??>
                    <img src="/img/${car.filename}" alt="${car.model}">
                <#else>
                    <img src="/static/images/defaultCarImage.png" alt="${car.model}">
                </#if>
            </td>
            <td><span>${car.manufacturer}</span></td>
            <td><span>${car.model}</span></td>
            <td><span>${car.modelYear}</span></td>
            <td><span>${car.mileage}</span></td>
            <td><strong>${car.ownerName}</strong></td>
            <td>
                <a href="/delete/${car.id}"><img class="icon" src="/static/icons/deleteIcon.png"></a>
                <a href="/edit/${car.id}"><img class="icon" src="/static/icons/editIcon.png"></a>
            </td>
        </tr>
    <#else>
        No cars yet
    </#list>
</table>

<@c.footer/>