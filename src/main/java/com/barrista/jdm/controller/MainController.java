package com.barrista.jdm.controller;

import com.barrista.jdm.domain.Car;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.CarRepo;
import com.barrista.jdm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class MainController
{
	@Autowired
	private CarRepo carRepo;

	private final CarService carService;

	@Value("${upload.path}")
	private String uploadPath;

	public MainController(CarService carService)
	{
		this.carService = carService;
	}

	@GetMapping("/")
	public String greeting(Map<String, Object> model)
	{
		return "greeting";
	}

	@GetMapping("/error")
	public String error(Model model)
	{
		return "error";
	}

	@GetMapping("/main")
	public String main(
			@RequestParam(required = false, defaultValue = "") String modelFilter,
			@RequestParam(required = false, defaultValue = "") String manufacturerFilter,
			Model model)
	{
		Iterable<Car> cars;

		if (modelFilter != null && !modelFilter.isEmpty()
				&& manufacturerFilter != null && !manufacturerFilter.isEmpty())
		{
			List<Car> carsToAdd = carRepo.findByModelContaining(modelFilter);
			carsToAdd.addAll(carRepo.findByManufacturerContaining(manufacturerFilter));
			cars = carsToAdd;
		}
		else if (modelFilter != null && !modelFilter.isEmpty())
		{
			cars = carRepo.findByModelContaining(modelFilter);
		}
		else if (manufacturerFilter != null && !manufacturerFilter.isEmpty())
		{
			cars = carRepo.findByManufacturerContaining(manufacturerFilter);
		}
		else
		{
			cars = carRepo.findAll();
		}

		model.addAttribute("cars", cars);
		model.addAttribute("modelFilter", modelFilter);
		model.addAttribute("manufacturerFilter", manufacturerFilter);

		return "main";
	}

	@PostMapping("/main")
	public String save(
			@AuthenticationPrincipal User user,
			@Valid Car car,
			BindingResult bindingResult,
			Model model,
			@RequestParam MultipartFile file
	) throws IOException
	{
		return carService.save(user, car, bindingResult, model, file);
	}

	@GetMapping("/delete/{carId}")
	public String delete(@PathVariable String carId) throws Exception
	{
		return carService.delete(carId);
	}

	@GetMapping("/edit/{carId}")
	public String edit(@PathVariable String carId, Model model)
	{
		return carService.edit(carId, model);
	}

	@PostMapping("/update/{carId}")
	public String update(
			@AuthenticationPrincipal User user,
			@Valid Car car,
			BindingResult bindingResult,
			Model model,
			@RequestParam MultipartFile file
	) throws IOException
	{
		return carService.update(user, car, bindingResult, model, file);
	}
}
