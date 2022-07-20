package com.example.doctorappdemo.controller;


import com.example.doctorappdemo.entity.User;
import com.example.doctorappdemo.service.SecurityService;
import com.example.doctorappdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static int ID = 0;

    //veikia
    @GetMapping("/login")
    public String loginForm(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/user-profile";
        }
        if (error != null)
            model.addAttribute("error", "Email or password incorect");
        if (logout != null)
            model.addAttribute("message", "You have been logged out");
        return "login";
    }


    //veikia
    @GetMapping("/userPage")
    public String viewDetails(@AuthenticationPrincipal UserDetails loggerUser, Model model) {

        if (loggerUser == null) {
            return "login";
        } else {


            String userName = loggerUser.getUsername();
            User user = userService.findByEmail(userName);


            if (user != null)
                ID = userService.findId(userName);

            User userFromDb = userService.findUserById(ID);


            if (Objects.equals(user, userFromDb)) {
                model.addAttribute("user", user);
            } else {
                model.addAttribute("user", userFromDb);
            }
            return "/admin_section/user-profile";
        }

    }


    @PostMapping("/userPage/update")
    public String updateDetails(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        System.out.println("user " + user.getEmail() + "  userPass " + user.getOldPassword());
        userValidator.validate(user, bindingResult);


        if (user.getOldPassword().equals("") && user.getNewPassword().equals("") && user.getPasswordNewConfirm().equals("")) {
            if (!(user.getOldEmail().equals("") || user.getNewEmail().equals("") || user.getEmailNewConfirm().equals(""))) {
                if (bindingResult.hasFieldErrors("newEmail") || bindingResult.hasFieldErrors("emailNewConfirm") || bindingResult.hasFieldErrors("oldEmail")) {
                    redirectAttributes.addFlashAttribute("error", "An error occured while saving the changes (email)");
                    return "redirect:/userPage";
                }
                redirectAttributes.addFlashAttribute("message", "Email saved successfuly");
                user.setEmail(user.getNewEmail());
                userService.saveNoPassword(user);
                return "redirect:/userPage";
            }
        } else if (user.getOldEmail().equals("") || user.getNewEmail().equals("") || user.getEmailNewConfirm().equals("")) {
            if (!(user.getOldPassword().equals("") || user.getNewPassword().equals("") || user.getPasswordNewConfirm().equals(""))) {
                if (bindingResult.hasFieldErrors("newPassword") || bindingResult.hasFieldErrors("passwordNewConfirm") || bindingResult.hasFieldErrors("oldPassword")) {
                    redirectAttributes.addFlashAttribute("error", "En error occured while saving the changes (password)");
                    return "redirect:/userPage";
                }
                user.setPassword(user.getNewPassword());
                userService.save(user);
                redirectAttributes.addFlashAttribute("message", "Password saved successfuly");
                return "redirect:/userPage";
            }
        }
        return "redirect:/admin_section/user-profile";
    }


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/user-profile")
    public String showUserProfile() {
        return "/admin_section/user-profile";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

}
