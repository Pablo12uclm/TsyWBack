package edu.uclm.esi.juegos.services;

import edu.uclm.esi.juegos.entities.User;
import edu.uclm.esi.juegos.exhandling.UserAlreadyExistsException;
import edu.uclm.esi.juegos.exhandling.UserNotFoundException;
import edu.uclm.esi.juegos.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User registerUser(String email, String username, String password) throws UserAlreadyExistsException {
        if (userDAO.findByEmail(email) != null) {
            throw new UserAlreadyExistsException("There is already a user registered with the email provided.");
        }
        if (userDAO.findByUsername(username) != null) {
            throw new UserAlreadyExistsException("There is already a user registered with the username provided.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password); // Assumes hashing is done within setPwd method
        return userDAO.save(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userDAO.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public User login(String email, String password) throws Exception {
        User user = userDAO.findByEmail(email);
        if (user == null || !user.getPassword().equals(Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString())) {
            throw new Exception("Invalid email or password");
        }
        return user;
    }
}
