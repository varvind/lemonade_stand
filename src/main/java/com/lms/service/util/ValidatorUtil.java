package com.lms.service.util;

import com.lms.model.User;
import com.lms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public class ValidatorUtil {

    @Autowired
    UserRepository userRepository;

    public String verifyUser(User user) {
        log.info("Verifying Valid User Information:\n" + user.toString());

        String errorMessage = "";
        if (Objects.equals(user.getFirstName(), "") || user.getFirstName() == null) {
            errorMessage += "firstname, ";
        }
        if (Objects.equals(user.getLastName(), "") || user.getLastName() == null) {
            errorMessage += "lastname, ";
        }
        if (Objects.equals(user.getPassword(), "") || user.getPassword() == null) {
            errorMessage += "password, ";
        }
        if (Objects.equals(user.getEmail(), "") || user.getEmail() == null) {
            errorMessage += "email, ";
        }
        if (userRepository.existsUserByEmail(user.getEmail())) {
            errorMessage += " Invalid Entry: Duplicate User. ";
        }

        if (!errorMessage.isEmpty()) {
            errorMessage = "Invalid or missing tokens: "
                    + errorMessage.substring(0, errorMessage.length() - 2) + ".";
        }
        return errorMessage;
    }
}
