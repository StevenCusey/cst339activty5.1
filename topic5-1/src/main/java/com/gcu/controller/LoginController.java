package com.gcu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import com.gcu.business.OrdersBusinessServiceInterface;
import com.gcu.business.SecurityBusinessService;
import com.gcu.model.LoginModel;

import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private OrdersBusinessServiceInterface ordersService;

    @Autowired
    private SecurityBusinessService securityService;

    @GetMapping("/login/")
    public String showLoginForm(Model model) {
        model.addAttribute("loginModel", new LoginModel());
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@Valid @ModelAttribute("loginModel") LoginModel loginModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Login Form");
            return "login";
        }

        if (securityService.authenticate(loginModel.getUsername(), loginModel.getPassword())) 
        {
            model.addAttribute("title", "My Orders");
            model.addAttribute("orders", ordersService.getOrders());
            return "orders";
        } 
        else 
        {
            model.addAttribute("title", "Login Form");
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }
}