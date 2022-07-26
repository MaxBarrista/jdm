package com.barrista.jdm.service;

import com.barrista.jdm.controller.ControllerUtils;
import com.barrista.jdm.controller.MainController;
import com.barrista.jdm.domain.Car;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.CarRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService
{
    private final CarRepo carRepo;

    public CarService(CarRepo carRepo)
    {
        this.carRepo = carRepo;
    }

    public String edit(String carId, Model model)
    {
        Optional<Car> carOpt = carRepo.findById(Long.valueOf(carId));
        if (carOpt.isPresent())
        {
            Car car = carOpt.get();
            model.addAttribute("car", car);
            return "carEdit";
        }
        return "redirect:/main";
    }

    public String delete(String carId) throws Exception
    {
        Optional<Car> temp = carRepo.findById(Long.valueOf(carId));
        temp.ifPresent(carRepo::delete);
        return "redirect:/main";
    }

    public void save(User user, Car car, BindingResult bindingResult, Model model, MultipartFile file) throws IOException
    {
        // If field validation checks failed
        if (bindingResult.hasErrors())
        {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("car", car);
        }
        else
        {
            car.setOwner(user);
            car.setPublished(new GregorianCalendar().getTime());
            if (file != null && !file.getOriginalFilename().isEmpty())
            {
                File uploadDir = new File(MainController.uploadPath);

                if (!uploadDir.exists())
                {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String fileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(MainController.uploadPath + "/" + fileName));

                car.setFilename(fileName);
            }
            carRepo.save(car);
            model.addAttribute("car", null);
        }
        Iterable<Car> cars = carRepo.findAll();
        model.addAttribute("cars", cars);
    }
}
