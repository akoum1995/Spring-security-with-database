package auth.example.akoum.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import auth.example.akoum.entities.User;
import auth.example.akoum.services.IServiceUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;


@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
    private IServiceUser userService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value="/users")
    public List<User> listUser(){
        return userService.getAll();
    }

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ADMIN')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/users/{id}")
    public User getOne(@PathVariable(value = "id") int id){
        return userService.findById(id);
    }

}
