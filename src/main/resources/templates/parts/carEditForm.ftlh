<a class="btn btn-primary mb-3" data-toggle="collapse" href="#addNewCar" role="button" aria-expanded="false"
   aria-controls="addNewCar">
    Add new car
</a>

<div class="collapse <#if car??>show</#if>" id="addNewCar">
    <div class="form-group mt-3">
        <form action="/main" method="post" enctype="multipart/form-data">
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
            <input type="hidden" name="id" value="<#if car??>${car.id}</#if>"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Save</button>
            </div>
        </form>
    </div>
</div>