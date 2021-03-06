package com.barrista.jdm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String manufacturer;
    private String model;
    private Integer modelYear;
    private Integer mileage;
    private Date published;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    private String filename;

    public Car()
    {
    }

    public Car(String manufacturer, String model, Integer modelYear, Integer mileage, User user, Date published)
    {
        this.manufacturer = manufacturer;
        this.model = model;
        this.modelYear = modelYear;
        this.mileage = mileage;
        this.owner = user;
        this.published = published;
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
