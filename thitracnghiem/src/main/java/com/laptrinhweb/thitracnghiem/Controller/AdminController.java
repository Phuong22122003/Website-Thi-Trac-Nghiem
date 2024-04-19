package com.laptrinhweb.thitracnghiem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/student")
    public String student() {
        return "/admin/student";
    }

    @GetMapping("/class")
    public String lophoc() {
        return "/admin/class";
    }

    @GetMapping("/lecturer")
    public String lecturer() {
        return "/admin/lecturer";
    }

    @GetMapping("/subject")
    public String subject() {
        return "/admin/subject";
    }
}
