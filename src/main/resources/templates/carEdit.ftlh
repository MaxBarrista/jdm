<#import "parts/common.ftlh" as c>

<@c.header />
<div class="mb-3 text-success">${message!}</div>
<div class="text-light">Car info</div>

<div class="form-group mt-3">
    <form method="post" action="/update/${car.id}" enctype="multipart/form-data">
        <div class="form-group">
            <input type="text" name="manufacturer"
                   class="form-control <#if manufacturerError??>is-invalid</#if>"
                   value="${car.manufacturer}" placeholder="Manufacturer:">
            <#if manufacturerError??>
                <div class="invalid-feedback">
                    ${manufacturerError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <input type="text" name="model" class="form-control <#if modelError??>is-invalid</#if>"
                   value="${car.model}" placeholder="Model:">
            <#if modelError??>
                <div class="invalid-feedback">
                    ${modelError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <input type="text" name="modelYear" class="form-control <#if modelYearError??>is-invalid</#if>"
                   value="<#if car.modelYear??>${car.modelYear?string.computer}</#if>" placeholder="Year:">
            <#if modelYearError??>
                <div class="invalid-feedback">
                    ${modelYearError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <input type="text" name="mileage" class="form-control <#if mileageError??>is-invalid</#if>"
                   value="<#if car.mileage??>${car.mileage?string.computer}</#if>" placeholder="Mileage:">
            <#if mileageError??>
                <div class="invalid-feedback">
                    ${mileageError}
                </div>
            </#if>
        </div>
        <div class="form-group car-image">
            <img src="<#if car.filename??>/img/${car.filename}<#else>/static/images/defaultCarImage.png</#if>"
                 alt="${car.manufacturer} ${car.model} ${car.modelYear}">
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" class="custom-file-input" id="customFile">
                <label class="custom-file-label" for="customFile">Upload a new photo</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group">
            <a href="/main">back</a>
            <button class="btn btn-primary" type="submit">Save</button>
        </div>
    </form>
</div>

<@c.footer />