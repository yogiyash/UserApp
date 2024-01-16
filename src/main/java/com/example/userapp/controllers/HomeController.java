package com.example.userapp.controllers;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.entity.User;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String todo() {
        return "index";
    }

    @GetMapping("/")
    public String index(){
        if(isAuthenticated())
            return "redirect:/users";

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        //if email exists
        if(Objects.nonNull(existingUser)){
            result.rejectValue("email","error1","try registering with a different email");
        }
        // if username exist
        else if (Objects.nonNull(userDto.getUsername()) && Objects.nonNull(userService.findUserByUsername(userDto.getUsername()))){
            result.rejectValue("username","error2","try with a different username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String userlist(Model model){
        List<UserDTO> list  =  userService.findAllUsers();
        model.addAttribute("users",list);
        return "users";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        authentication.getAuthorities().stream().forEach(System.out::println);

        return authentication.isAuthenticated();
    }
}