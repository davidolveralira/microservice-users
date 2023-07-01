package org.dolvera.springcloud.msvc.users.services;

import org.dolvera.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    Optional<User> byId(Long id);
    User save(User user);
    void delete(Long id);
}
