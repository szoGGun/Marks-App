package org.przemekrutkowski.exammarksapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.przemekrutkowski.exammarksapp.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class SsoController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        log.info("user is logged out");
        return ViewNames.HOME;
    }
}
