package com.example.eccomerce.controller;

import com.example.eccomerce.service.CurrencyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
// ⭐️ CONCEPT: Authorization (Class Level)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final CurrencyService currencyService;

    public AdminController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public String showRateUpdateForm(Model model) {
        model.addAttribute("message", "Only Admin users can see this page.");
        return "admin/rate_update";
    }

    @PostMapping("/rates/update")
    public String updateRate(@RequestParam String code, @RequestParam double rate, RedirectAttributes ra) {
        currencyService.updateRate(code, rate);
        ra.addFlashAttribute("message", "Currency rate updated for " + code);
        return "redirect:/admin/rates";
    }
}