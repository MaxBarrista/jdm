package com.barrista.jdm.repos;

import com.barrista.jdm.domain.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepo extends CrudRepository<Car, Long>
{
    List<Car> findByModelContaining(String filter);
}
