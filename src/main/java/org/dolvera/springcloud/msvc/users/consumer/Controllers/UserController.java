package org.dolvera.springcloud.msvc.users.consumer.Controllers;

import org.dolvera.springcloud.msvc.users.models.entity.User;
import org.dolvera.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/springcloud/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/obtain")
    public ResponseEntity<List<User>> obtain() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        Optional<User> userOptional = service.byId(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        Optional<User> o = service.byId(id);
        if (o.isPresent()) {
            User userDB = o.get();
            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> erase(@PathVariable Long id) {
        Optional<User> o =service.byId(id);
        if (o.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
