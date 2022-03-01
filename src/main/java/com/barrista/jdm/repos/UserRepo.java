package com.barrista.jdm.repos;

import com.barrista.jdm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
