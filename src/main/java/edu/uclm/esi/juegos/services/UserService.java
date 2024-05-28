package edu.uclm.esi.juegos.services;

import edu.uclm.esi.juegos.entities.User;
import edu.uclm.esi.juegos.exhandling.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
    User registerUser(String email, String username, String password) throws UserAlreadyExistsException;
    User findUserByEmail(String email);
    User findUserById(Long id);
    List<User> getAllUsers();
    User login(String email, String password) throws Exception; // Añadimos el método login
}
