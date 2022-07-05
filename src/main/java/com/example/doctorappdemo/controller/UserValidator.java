package com.example.doctorappdemo.controller;

import com.example.doctorappdemo.entity.User;
import com.example.doctorappdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User fromDbPassword = userService.findUserByEmail(user.getEmail());
        User fromDbEmail = userService.findByEmail(user.getEmail());


//        Email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","NotEmpty");
        if (user.getEmail().length() < 8 || user.getEmail().length() > 16){
            errors.rejectValue("email","Size.userForm.email");
        }
        if (userService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }


//        Password
        if (user.getPassword() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }
        }

        if (user.getPasswordConfirm() != null) {
            if (!user.getPasswordConfirm().equals(user.getPassword())) {
                errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
            }
        }

//        ----NEW password-----//

        if (user.getNewPassword() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty");
            if (user.getNewPassword().length() < 8 || user.getNewPassword().length() > 32) {
                errors.rejectValue("newPassword", "Size.userForm.newPassword");
            }
        }

        if (user.getOldPassword() != null) {
            if (!(bCryptPasswordEncoder.matches(user.getOldPassword(), fromDbPassword.getPassword()))) {
                errors.rejectValue("oldPassword", "Diff.userForm.oldPassword");
            }
        }

        if (user.getPasswordNewConfirm() != null) {
            if (!user.getPasswordNewConfirm().equals(user.getNewPassword())) {
                errors.rejectValue("passwordNewConfirm", "Diff.userForm.getPasswordNewConfirm");
            }
        }
//        NEW Email
        if (user.getNewEmail() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newEmail", "NotEmpty");
            if (user.getNewEmail().length() < 6 || user.getNewEmail().length() > 32) {
                errors.rejectValue("newEmail", "Size.userForm.newEmail");
            }
            if (userService.findByEmail(user.getNewEmail()) != null) {
                errors.rejectValue("newEmail", "Duplicate.userForm.newEmail");
            }

            if (!user.getNewEmail().equals(user.getEmailNewConfirm())) {
                errors.rejectValue("emailNewConfirm", "Diff.userForm.emailConfirm");
            }

            if (!user.getOldEmail().equals(fromDbEmail.getEmail())) {
                errors.rejectValue("oldEmail", "Diff.userForm.oldEmail");
            }
        }

    }
}
