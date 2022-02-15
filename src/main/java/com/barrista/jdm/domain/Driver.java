package com.barrista.jdm.domain;


import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Driver
{
		@Id
		@GeneratedValue(strategy= GenerationType.AUTO)
		private Integer id;

		private String driversLicense;

		private String name;

		private Integer age;

		private Integer drivingExperience;

		public Integer getId()
		{
				return id;
		}

		public void setId(Integer id)
		{
				this.id = id;
		}

		public String getDriversLicense()
		{
				return driversLicense;
		}

		public void setDriversLicense(String driversLicense)
		{
				this.driversLicense = driversLicense;
		}

		public String getName()
		{
				return name;
		}

		public void setName(String name)
		{
				this.name = name;
		}

		public Integer getAge()
		{
				return age;
		}

		public void setAge(Integer age)
		{
				this.age = age;
		}

		public Integer getDrivingExperience()
		{
				return drivingExperience;
		}

		public void setDrivingExperience(Integer drivingExperience)
		{
				this.drivingExperience = drivingExperience;
		}

}
