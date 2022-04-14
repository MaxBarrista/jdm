<#import "parts/common.ftlh" as c>

<@c.header />
<div class="text-light">Car info</div>


<div class="form-group mt-3">
    <form method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="text" class="form-control" name="manufacturer" value="${car.manufacturer}">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="model" value="${car.model}">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="modelYear" value="${car.modelYear}">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="mileage" value="${car.mileage}">
        </div>
        <div class="form-group car-image">
            <#if car.filename??>
                <img src="/img/${car.filename}" alt="${car.model}">
            <#else>
                <img src="/static/images/defaultCarImage.png" alt="${car.model}">
            </#if>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group">
            <a href="/main">back</a>
            <button class="btn btn-primary ml-1" type="submit">Save</button>
        </div>
    </form>
</div>

<@c.footer />