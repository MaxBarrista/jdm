package com.barrista.jdm.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Manufacturer can not be empty.")
    private String manufacturer;
    @NotBlank(message = "Model can not be empty.")
    private String model;
    @NotNull(message = "Model year can not be empty.")
    @Min(value = 1768, message = "Please enter the correct year.")
    @Max(value = 2100, message = "Please enter the correct year.")
    private Integer modelYear;
    @NotNull(message = "Mileage can not be empty.")
    @Positive
    private Integer mileage;
    private Date published;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    private String filename;

    public Car()
    {
    }

    public Car(String manufacturer, String model, Integer modelYear, Integer mileage, User user)
    {
        this.manufacturer = manufacturer;
        this.model = model;
        this.modelYear = modelYear;
        this.mileage = mileage;
        this.owner = user;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public Integer getModelYear()
    {
        return modelYear;
    }

    public void setModelYear(Integer modelYear)
    {
        this.modelYear = modelYear;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public String getOwnerName()
    {
        return owner == null ? "" : owner.getUsername();
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public Date getPublished()
    {
        return published;
    }

    public void setPublished(Date published)
    {
        this.published = published;
    }
}
