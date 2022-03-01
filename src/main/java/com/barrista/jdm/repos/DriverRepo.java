package com.barrista.jdm.repos;

import com.barrista.jdm.domain.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepo extends CrudRepository<Driver, Integer>
{
    List<Driver> findByNameContaining(String filter);
}
