package org.przemekrutkowski.exammarksapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.model.User;
import org.przemekrutkowski.exammarksapp.model.dto.PasswordChangeDto;
import org.przemekrutkowski.exammarksapp.service.UserService;
import org.przemekrutkowski.exammarksapp.util.AttributeNames;
import org.przemekrutkowski.exammarksapp.util.Mappings;
import org.przemekrutkowski.exammarksapp.util.ViewNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.przemekrutkowski.exammarksapp.enums.SecurityRole.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    public static final String ACCOUNT_CREATED_MESSAGE = "Account for user with name: %s has been created. Please log in!";
    private final UserService userService;


    @GetMapping(Mappings.USER_ADMINISTRATION)
    public String users(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                        @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                        final Model model) {
        Page<User> usersPage = userService.findAll(PageRequest.of(pageNumber - 1, pageSize));
        model.addAttribute(AttributeNames.USER_PAGE, usersPage);
        Stream.of(usersPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .forEach(pageNumbers -> model.addAttribute(AttributeNames.PAGE_NUMBERS, pageNumbers));
        return ViewNames.USER_ADMINISTRATION;
    }

    @GetMapping(Mappings.ADD_USER)
    public String addUser(final Model model) {
        var userRoles = roles();
        userRoles.removeIf(role -> role.equals(ROLE_GUEST.getShortName()));
        model.addAttribute(AttributeNames.USER, new User());
        model.addAttribute(AttributeNames.USER_ROLES, userRoles);
        return ViewNames.ADD_USER;
    }

    @GetMapping(Mappings.EDIT_USER)
    public String editUser(@RequestParam final String userName, final Model model) {
        var user = userService.getUser(userName);
        user.setPassword(null);
        model.addAttribute(AttributeNames.USER, user);
        return ViewNames.EDIT_USER;
    }

    @PostMapping(Mappings.ADD_USER)
    public String performAddUser(@ModelAttribute(AttributeNames.USER) @Valid final User user,
                                 BindingResult bindingResult,
                                 final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(AttributeNames.USER, user);
            return ViewNames.ADD_USER;
        }

        if (user.getUserRole() == null) {
            user.setUserRole(ROLE_USER.getShortName());
            model.addAttribute(AttributeNames.MESSAGE, ACCOUNT_CREATED_MESSAGE.formatted(user.getUserName()));
            if (userService.addUser(user)) {
                return ViewNames.LOGIN;
            } else {
                userConflictError(user, bindingResult);
                return ViewNames.ADD_USER;
            }
        } else {
            userService.addUser(user);
        }
        userConflictError(user, bindingResult);
        return "redirect:/" + Mappings.USER_ADMINISTRATION;
    }

    public void userConflictError(final User user, final BindingResult bindingResult) {
        final String userConflictMessage = "User with name %s already exist".formatted(user.getUserName());
        bindingResult.addError(new ObjectError("message", userConflictMessage));
    }

    @PostMapping(Mappings.EDIT_USER)
    public String performEditUser(
            @ModelAttribute(AttributeNames.USER) @Valid final User user,
            final BindingResult bindingResult,
            final Model model) {
        if (!bindingResult.hasErrors()) {
            userService.editUser(user);
            return "redirect:/" + Mappings.USER_ADMINISTRATION;
        }
        model.addAttribute(AttributeNames.USER, user);
        return ViewNames.EDIT_USER;
    }

    @GetMapping(Mappings.PASSWORD_CHANGE)
    public String changePassword(final Model model) {
        model.addAttribute(AttributeNames.PASSWORD_CHANGE, new PasswordChangeDto());
        return ViewNames.PASSWORD_CHANGE;
    }

    @PostMapping(Mappings.PASSWORD_CHANGE)
    public String performPasswordChange(
            @ModelAttribute(AttributeNames.PASSWORD_CHANGE) @Valid final PasswordChangeDto passwordChangeDto,
            BindingResult bindingResult,
            Model model, Principal principal) {
        if (!bindingResult.hasErrors()) {
            log.info("Changing password for user={}", principal.getName());
            userService.changePassword(principal.getName(), passwordChangeDto);
            model.addAttribute(Map.of(
                    AttributeNames.MESSAGE, "Password successfully updated!",
                    AttributeNames.PASSWORD_CHANGE, new PasswordChangeDto()));
        } else {
            model.addAttribute(AttributeNames.PASSWORD_CHANGE, passwordChangeDto);
        }
        return ViewNames.PASSWORD_CHANGE;
    }

    @GetMapping(Mappings.DELETE_USER)
    public String deleteUser(@RequestParam final String userName) {
        userService.delete(userName);
        return "redirect:/" + Mappings.USER_ADMINISTRATION;
    }
}
