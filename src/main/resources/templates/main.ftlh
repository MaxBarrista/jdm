<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#include "parts/security.ftlh">

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

<#include "parts/carEditForm.ftlh" />

<#include "parts/carList.ftlh" />

<@c.footer/>