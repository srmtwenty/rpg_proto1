package com.scott.controllers;

import com.scott.models.Nation;
import com.scott.models.User;
import com.scott.repositories.NationRepository;
import com.scott.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    NationRepository nationRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User has not been found!"));
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        User updateU=this.findUserById(id);
        updateU.setName(user.getName());
        return userRepository.save(updateU);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}/assignNation/{nationId}")
    public User assignNation(@PathVariable Long id, @PathVariable Long nationId){
        User user=userRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        user.setNation(nation);
        System.out.println("updated!");
        return userRepository.save(user);
    }
    @PutMapping("/{id}/removeNation/{nationId}")
    public User removeNation(@PathVariable Long id, @PathVariable Long nationId){
        User person=userRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        person.removeNation(nation);
        System.out.println("updated!");
        return userRepository.save(person);
    }
}
