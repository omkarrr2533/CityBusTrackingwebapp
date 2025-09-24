package com.citybus.service;

import com.citybus.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private List<User> users = new ArrayList<>();

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initUsers() {
        users.add(new User(1L, "drvr-1", "driver1", passwordEncoder.encode("password123"), "driver", "bus-1"));
        users.add(new User(2L, "drvr-2", "driver2", passwordEncoder.encode("password123"), "driver", "bus-2"));
        users.add(new User(3L, "drvr-3", "driver3", passwordEncoder.encode("password123"), "driver", "bus-3"));
        users.add(new User(4L, "drvr-4", "driver4", passwordEncoder.encode("password123"), "driver", "bus-4"));
        users.add(new User(5L, "drvr-5", "driver5", passwordEncoder.encode("password123"), "driver", "bus-5"));
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}