package com.barrista.jdm;

import com.barrista.jdm.domain.Driver;
import com.barrista.jdm.repos.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController
{
	@Autowired
	private DriverRepo driverRepo;

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
						   Map<String, Object> model)
	{
		model.put("name", name);
		return "greeting";
	}

	@GetMapping
	public String main(Map<String, Object> model)
	{
		Iterable<Driver> drivers = driverRepo.findAll();

		model.put("drivers", drivers);

		return "main";
	}

	@PostMapping
	public String save(@RequestParam String licence,
					   @RequestParam String name,
					   @RequestParam Integer age,
					   @RequestParam Integer experience,
					   Map<String, Object> model)
	{
		Driver driver = new Driver(licence, name, age, experience);
		Iterable<Driver> drivers = driverRepo.findAll();

		driverRepo.save(driver);
		model.put("drivers", drivers);

		return "main";
	}
}
