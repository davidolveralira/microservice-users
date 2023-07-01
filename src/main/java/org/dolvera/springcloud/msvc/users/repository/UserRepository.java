package org.dolvera.springcloud.msvc.users.repository;

import org.dolvera.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {



}
