package org.dolvera.springcloud.msvc.users.consumer.Controllers;

import org.dolvera.springcloud.msvc.users.models.entity.User;
import org.dolvera.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }

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

    //    Utility method Validate
    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "The field:" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
