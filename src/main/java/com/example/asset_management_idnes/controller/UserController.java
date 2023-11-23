package com.example.asset_management_idnes.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequestMapping
public class UserController {


    @GetMapping("/api/test/admin")
    @ResponseBody
    public boolean adminAccess() {
        return true;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "http://localhost:4200/admin/login";
    }

    @GetMapping()
    public String getLoginPage(Model model) {
        return "home";
    }
}
