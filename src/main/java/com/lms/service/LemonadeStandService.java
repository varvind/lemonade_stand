package com.lms.service;

import com.lms.model.Networth;
import com.lms.model.Source;
import com.lms.model.User;
import com.lms.repository.NetworthRepository;
import com.lms.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class LemonadeStandService {
    @Autowired
    ValidatorUtil validatorUtil;

    @Autowired
    UserService userService;

    @Autowired
    NetworthRepository networthRepository;

    public String addSource(Source source) {
        String validation = validatorUtil.validateSource(source);

        if (!validation.isEmpty()) {
            log.error("Error in user request: {}", validation);
            return validation;
        }

        User user = userService.getUser();

        if (user == null) {
            log.error("Invalid user or user not logged in");
            return "Invalid user, or user not logged in";
        }

        // Grab Networth object to update
        Networth networth;
        if (user.getCurrentNetworth() == null) {
            user.setCurrentNetworth(new Networth());
            user.addNetworth(user.getCurrentNetworth());
            log.info("No existing networth found, creating new networth for user.");
        } else if (isNewMonth(user.getCurrentNetworth())) {
            // Copying existing networth for new month
            Networth newNetworth = new Networth();
            Networth existingNetworth = user.getCurrentNetworth();

            // Copy the existing networth details
            newNetworth.setCurrent_amount(existingNetworth.getCurrent_amount()); // Copy current amount
            newNetworth.setSources(new ArrayList<>(existingNetworth.getSources())); // Copy sources to new list

            user.setCurrentNetworth(newNetworth);
            user.addNetworth(newNetworth);

            log.info("New month detected, copied networth for user.");
        }

        // Add the new source to the current networth
        networth = user.getCurrentNetworth();
        networth.addSource(source);
        log.info("Adding source: {} to current networth.", source.getName()); // Or any relevant info from the source

        // Save the updated networth
        networthRepository.save(networth);

        return ""; // Return empty string indicating success
    }


    public boolean isNewMonth(Networth currentNetworth) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Extract the month and year from the current date
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Extract the month and year from the date_created of the networth
        LocalDate dateCreated = currentNetworth.getDate_created().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        int createdMonth = dateCreated.getMonthValue();
        int createdYear = dateCreated.getYear();

        // Compare the month and year
        return currentMonth != createdMonth || currentYear != createdYear;
    }
}
