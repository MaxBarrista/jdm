package com.barrista.jdm.controller;

import com.barrista.jdm.domain.Car;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController
{
	@Autowired
	private CarRepo carRepo;

	@Value("${upload.path}")
	private String uploadPath;

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
			@RequestParam String manufacturer,
			@RequestParam(name="model") String carModel,
			@RequestParam Integer modelYear,
			@RequestParam Integer mileage,
			@AuthenticationPrincipal User user,
			@RequestParam("file") MultipartFile file,
			Model model
	) throws IOException
	{
		Calendar calendar = new GregorianCalendar();
		Car car = new Car(manufacturer, carModel, modelYear, mileage, user, calendar.getTime());

		if(file != null && !file.getOriginalFilename().isEmpty())
		{
			File uploadDir = new File(uploadPath);

			if(!uploadDir.exists())
			{
				uploadDir.mkdir();
			}
			String uuidFile = UUID.randomUUID().toString();
			String fileName = uuidFile + "." + file.getOriginalFilename();

			file.transferTo(new File(uploadPath + "/" + fileName));

			car.setFilename(fileName);
		}
		carRepo.save(car);

		Iterable<Car> cars = carRepo.findAll();
		model.addAttribute("cars", cars);

		return "main";
	}

	@GetMapping("/delete/{carId}")
	public String delete(@PathVariable String carId)
	{
		List<Car> temp = carRepo.findById(Integer.valueOf(carId));
		if (temp != null && temp.size() > 0)
		{
			carRepo.delete(temp.get(0));
		}
		return "redirect:/main";
	}

	@GetMapping("/edit/{carId}")
	public String edit(
			@PathVariable String carId,
			Model model
	)
	{
		List<Car> temp = carRepo.findById(Integer.valueOf(carId));
		if (temp != null && temp.size() > 0)
		{
			Car car = temp.get(0);
			model.addAttribute("car", car);
			return "carEdit";
		}

		return "redirect:/main";
	}
}
