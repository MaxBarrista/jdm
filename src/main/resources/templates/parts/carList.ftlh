<table class="car-list">
    <tr class="table-header">
        <td><span>Image</span></td>
        <td><span>Manufacturer</span></td>
        <td><span>Model</span></td>
        <td><span>Year</span></td>
        <td><span>Mileage, km.</span></td>
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
            <td><strong><span><a href="/user-cars/${car.owner.id}">${car.ownerName}</a></span></strong></td>
            <td>
                <#if car.owner.id == currentUserId || isAdmin>
                    <a href="/edit/${car.id}"><img class="icon" src="/static/icons/editIcon.png"></a>
                    <a href="/delete/${car.id}"
                       onclick="return confirm('Are you sure about deleting the car ${car.manufacturer} ${car.model}')">
                        <img class="icon" src="/static/icons/deleteIcon.png">
                    </a>
                </#if>
            </td>
        </tr>
    <#else>
        No cars yet
    </#list>
</table>