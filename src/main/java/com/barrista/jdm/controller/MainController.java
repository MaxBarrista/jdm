package com.barrista.jdm.controller;

import com.barrista.jdm.domain.Driver;
import com.barrista.jdm.repos.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController
{
	@Autowired
	private DriverRepo driverRepo;

	@GetMapping("/")
	public String greeting(Map<String, Object> model)
	{
		return "greeting";
	}

	@GetMapping("/main")
	public String main(Map<String, Object> model)
	{
		Iterable<Driver> drivers = driverRepo.findAll();

		model.put("drivers", drivers);

		return "main";
	}

	@PostMapping("/main")
	public String save(@RequestParam String licence,
					   @RequestParam String name,
					   @RequestParam Integer age,
					   @RequestParam Integer experience,
					   Map<String, Object> model)
	{
		Driver driver = new Driver(licence, name, age, experience);
		driverRepo.save(driver);
		Iterable<Driver> drivers = driverRepo.findAll();
		model.put("drivers", drivers);

		return "main";
	}

	@PostMapping("filter")
	public String filter(@RequestParam String filter, Map<String, Object> model)
	{
		Iterable<Driver> drivers;
		if (filter != null && !filter.isEmpty())
		{
			drivers = driverRepo.findByNameContaining(filter);
		}
		else
		{
			drivers = driverRepo.findAll();
		}
		model.put("drivers", drivers);
		return "main";
	}
}
