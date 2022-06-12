package org.przemekrutkowski.exammarksapp.controller;

import org.przemekrutkowski.exammarksapp.util.Mappings;
import org.przemekrutkowski.exammarksapp.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(Mappings.HOME)
    public String goHome() {
        return ViewNames.HOME;
    }
}
