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
<div class="collapse <#if car??>show</#if>" id="addNewCar">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="manufacturer"
                       class="form-control <#if manufacturerError??>is-invalid</#if>"
                       value="<#if car??>${car.manufacturer}</#if>" placeholder="Manufacturer:">
                <#if manufacturerError??>
                    <div class="invalid-feedback">
                        ${manufacturerError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="model" class="form-control <#if modelError??>is-invalid</#if>"
                       value="<#if car??>${car.model}</#if>" placeholder="Model:">
                <#if modelError??>
                    <div class="invalid-feedback">
                        ${modelError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="modelYear" class="form-control <#if modelYearError??>is-invalid</#if>"
                       value="<#if car??><#if car.modelYear??>${car.modelYear?string.computer}</#if></#if>" placeholder="Year:">
                <#if modelYearError??>
                    <div class="invalid-feedback">
                        ${modelYearError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="mileage" class="form-control <#if mileageError??>is-invalid</#if>"
                       value="<#if car??><#if car.mileage??>${car.mileage?string.computer}</#if></#if>" placeholder="Mileage:">
                <#if mileageError??>
                    <div class="invalid-feedback">
                        ${mileageError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Photo</label>
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
        <td><span>Image</span></td>
        <td><span>Manufacturer</span></td>
        <td><span>Model</span></td>
        <td><span>Year</span></td>
        <td><span>Mileage</span></td>
        <td><span>Published</span></td>
        <td><strong><span>Owner</span></strong></td>
        <td><span>Actions</span></td>
    </tr>
    <#list cars as car>
        <tr>
            <td class="car-image">
                <#if car.filename??>
                    <img src="/img/${car.filename}" alt="${car.model}">
                <#else>
                    <img src="/static/images/defaultCarImage.png" alt="${car.model}">
                </#if>
            </td>
            <td><span>${car.manufacturer}</span></td>
            <td><span>${car.model}</span></td>
            <td><span>${car.modelYear?string.computer}</span></td>
            <td><span>${car.mileage?string.computer}</span></td>
            <td>
                <#if car.published??>
                    <span>${car.published?date}</span><br>
                    <span>${car.published?time}</span>
                </#if>
            </td>
            <td><strong><span>${car.ownerName}</span></strong></td>
            <td>
                <a href="/edit/${car.id}"><img class="icon" src="/static/icons/editIcon.png"></a>
                <a href="/delete/${car.id}"><img class="icon" src="/static/icons/deleteIcon.png"></a>
            </td>
        </tr>
    <#else>
        No cars yet
    </#list>
</table>

<@c.footer/>