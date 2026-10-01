package com.crophealth.monitor.service;

import com.crophealth.monitor.entity.User;
import com.crophealth.monitor.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String email, String password) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already registered");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = getUserById(userId);
        if(user != null) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(user);
        }
    }
    
    public void updateName(Long userId, String name) {
        User user = getUserById(userId);
        if(user != null) {
            user.setName(name);
            userRepository.save(user);
        }
    }
}
