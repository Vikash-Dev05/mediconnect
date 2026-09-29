package com.mediconnect.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

import com.mediconnect.repository.CategoryRepository;
import com.mediconnect.entity.Doctor;
import com.mediconnect.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class DashboardController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private com.mediconnect.repository.UserRepository userRepository;

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("DEBUG: User " + authentication.getName() + " has roles: " + roles);
        
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_PATIENT")) {
            return "redirect:/patient/dashboard";
        } else if (roles.contains("ROLE_DOCTOR")) {
            return "redirect:/doctor/dashboard";
        }
        
        System.out.println("DEBUG: No matching role found, falling back to login.");
        return "redirect:/login?error=no_role";
    }

    @GetMapping("/pending-approval")
    public String pendingApproval() {
        return "pending_approval";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "signup";
    }
}
