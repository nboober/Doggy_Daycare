package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    DogRepository dogRepository;

    //Home
    @RequestMapping("/")
    public String index(Model model){
        //If there is a user logged in get the user
        model.addAttribute("user", userRepository.findAll());
        if(userService.getUser() != null) {
            model.addAttribute("user", userService.getUser());
        }
        return "index";
    }

    @GetMapping("/add")
    public String addDog(Model model){

        model.addAttribute("dog", new Dog());

        return "addDog";
    }

    @PostMapping("/processDog")
    public String processDog(@Valid Dog dog, BindingResult result, @ModelAttribute User user){
        if(result.hasErrors()){
            return "addDog";
        }

//        userService.getUser().setDogs(dog);
        userService.saveUser(user);

        dog.setOwner(userService.getUser());
        dogRepository.save(dog);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userRepository.findById(id).get());
        return "updateUser";
    }

    @PostMapping("/process")
    public String processUpdates(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){

        model.addAttribute("user", user);
        if(result.hasErrors()){
            return "updateUser";
        }

        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model){
        userRepository.deleteById(id);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }






}
