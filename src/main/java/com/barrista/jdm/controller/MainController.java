package com.barrista.jdm.controller;

import com.barrista.jdm.domain.Car;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController
{
	@Autowired
	private CarRepo carRepo;

	@GetMapping("/")
	public String greeting(Map<String, Object> model)
	{
		return "greeting";
	}

	@GetMapping("/main")
	public String main(@RequestParam(required = false, defaultValue = "") String carModel, Model model)
	{
		Iterable<Car> cars = carRepo.findAll();

		if (carModel != null && !carModel.isEmpty())
		{
			cars = carRepo.findByModelContaining(carModel);
		}
		else
		{
			cars = carRepo.findAll();
		}

		model.addAttribute("cars", cars);
		model.addAttribute("carModel", carModel);

		return "main";
	}

	@PostMapping("/main")
	public String save(
			@RequestParam String manufacturer,
			@RequestParam(name="model") String carModel,
			@RequestParam Integer modelYear,
			@RequestParam Integer mileage,
			@AuthenticationPrincipal User user,
			Model model)
	{
		Car car = new Car(manufacturer, carModel, modelYear, mileage, user);
		carRepo.save(car);
		Iterable<Car> cars = carRepo.findAll();
		model.addAttribute("cars", cars);

		return "main";
	}
}
