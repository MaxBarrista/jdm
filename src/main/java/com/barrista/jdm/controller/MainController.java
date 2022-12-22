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
import java.util.Set;

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

	@GetMapping("/edit/{car}")
	public String editCar(@AuthenticationPrincipal User user, @PathVariable Car car, Model model)
	{
		if (!user.isAdmin() && !user.equals(car.getOwner()))
		{
			return "error";
		}
		model.addAttribute("car", car);
		return "carEdit";
	}

	@PostMapping("/edit/{oldCar}")
	public String updateCar(
			@AuthenticationPrincipal User user,
			@PathVariable Car oldCar,
			@Valid Car newCar,
			BindingResult bindingResult,
			Model model,
			@RequestParam MultipartFile file
	) throws IOException
	{
//		/**
//		 * @author IrishkaPoopsen
//		 * @since 2022.08.12
//		 */
		// блииииин класс не работает!!! какой капец!!!!! девочки это кошмар настоящий, класс не работает,
		// я программировал его чтобы он работал а он не работает!!!!!!
		// какая-то жесть!!!!!!!!!!!!!!!!!!
		// If the user has the access to modify car info
		if (!user.isAdmin() && !user.equals(oldCar.getOwner()))
		{
			return "error";
		}
		if (bindingResult.hasErrors())
		{
			Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
			model.mergeAttributes(errorsMap);
		}
		else
		{
			carService.update(oldCar, newCar, file);
			model.addAttribute("message", "Saved!");
		}
		newCar.setId(oldCar.getId());
		newCar.setFilename(oldCar.getFilename());
		model.addAttribute("car", newCar);
		return "carEdit";
	}

	@GetMapping("/user-cars/{user}")
	public String userCars(
			@AuthenticationPrincipal User currentUser,
			@PathVariable User user,
			Model model
	) {
		Set<Car> cars = user.getCars();
		model.addAttribute("userChannel", user);
		model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
		model.addAttribute("subscribersCount", user.getSubscribers().size());
		model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
		model.addAttribute("cars", cars);
		model.addAttribute("isCurrentUser", currentUser.equals(user));
		return "userCars";
	}
}
