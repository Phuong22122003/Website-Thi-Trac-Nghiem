package com.laptrinhweb.thitracnghiem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Service.LopHocService;

@Controller
@RequestMapping("/admin")
public class AdminClassController {
    @Autowired
    private LopHocService lopHocService;

    @ModelAttribute("allClass")
    public List<Lop> classes() {
        return lopHocService.getAllClass();
    }

    @ModelAttribute("class")
    public Lop newClass() {
        return new Lop();
    }

    @ModelAttribute("editClass")
    public Lop editClass() {
        return new Lop();
    }

    @GetMapping("/classes")
    public String getAllClass() {
        return "/admin/classes";
    }

    @PostMapping("/deleteClass")
    public String deleteClass(@RequestParam("maLop") String maLop, RedirectAttributes redirectAttributes) {
        try {
            lopHocService.deleteClass(maLop);
            redirectAttributes.addFlashAttribute("message", "Xóa lớp thành công");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Xóa lớp thất bại! " + ex.getMessage());
        }
        return "redirect:classes";
    }

    @PostMapping("/addClass")
    public String addNewClass(@Validated Lop lop, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại! Vui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.class", errors);
            redirectAttributes.addFlashAttribute("class", lop);
            return "redirect:classes";
        }
        if (lopHocService.checkExistsMaLop(lop.getMaLop())) {
            errors.rejectValue("maLop", "class", "Mã lớp đã tồn tại");
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại! Vui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.class", errors);
            redirectAttributes.addFlashAttribute("class", lop);
            return "redirect:classes";
        }
        // check tên lớp nếu nó là khóa duy nhất
        try {
            lopHocService.addNewClass(lop);
            redirectAttributes.addFlashAttribute("message", "Thêm thành công");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại! " + ex.getMessage());
            redirectAttributes.addFlashAttribute("class", lop);
        }
        return "redirect:classes";
    }

    @PostMapping("/editClass")
    public String editClass(@Validated Lop lop, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Sửa thất bại\nVui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editClass", errors);
            redirectAttributes.addFlashAttribute("editClass", lop);
            return "redirect:classes";
        }
        // check tên lớp nếu nó là khóa duy nhất
        try {
            lopHocService.editClass(lop);
            redirectAttributes.addFlashAttribute("message", "Sửa thành công");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Sửa thất bại\n" + ex.getMessage());
            redirectAttributes.addFlashAttribute("editClass", lop);
        }
        return "redirect:classes";
    }
}
