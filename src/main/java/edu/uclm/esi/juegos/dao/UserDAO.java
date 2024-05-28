package edu.uclm.esi.juegos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.uclm.esi.juegos.entities.User;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
