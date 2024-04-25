package com.laptrinhweb.thitracnghiem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Service.MonHocService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    MonHocService monHocService;

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
    public String subject(ModelMap model) {
        List<MonHoc> monHocList = monHocService.getAllMonHoc();
        model.addAttribute("monHocList", monHocList);
        return "/admin/subject";
    }

    @GetMapping("/deleteMonHoc/{mamh}")
    public String deleteMonHoc(@PathVariable String mamh, ModelMap model, RedirectAttributes redirectAttributes) {
        int statusXoaMonHoc = monHocService.deleteByMamh(mamh);
        redirectAttributes.addFlashAttribute("statusXoaMonHoc", statusXoaMonHoc);
        return "redirect:/admin/subject";
    }

    @ModelAttribute("monHoc")
    public MonHoc createNewMonHoc() {
        return new MonHoc();
    }

    // @PostMapping("/addMonHoc")
    // public String addMonHoc(@RequestParam(name = "mamh") String mamh,
    // @RequestParam(name = "tenmh") String tenmh,
    // @RequestParam(name = "soTietLt") int soTietLt, @RequestParam(name =
    // "soTietTh") int soTietTh,
    // RedirectAttributes redirectAttributes) {
    // MonHoc monHoc = new MonHoc(mamh, tenmh, soTietLt, soTietTh, false);
    // monHoc.setTrangThaiXoa(false);
    // int statusThemMonHoc = monHocService.addMonHoc(monHoc);
    // redirectAttributes.addFlashAttribute("statusThemMonHoc", statusThemMonHoc);
    // return "redirect:/admin/subject";
    // }

    @PostMapping("/addMonHoc")
    public String addMonHoc(@ModelAttribute("monHoc") MonHoc monHoc,
            RedirectAttributes redirectAttributes, BindingResult errors) {
        String errorMessage = "";

        if (monHoc.getMamh().trim().length() == 0) {
            errors.rejectValue("mamh", "monHoc", "Mã môn học không được bỏ trống");
        }
        if (monHoc.getTenmh().trim().length() == 0) {
            errors.rejectValue("tenmh", "monHoc", "Tên môn học không được bỏ trống");
        }
        if (monHoc.getSoTietLt() < 0) {
            errors.rejectValue("soTietLt", "monHoc", "Số tiết lý thuyết không thể < 0");
        }
        if (monHoc.getSoTietTh() < 0) {
            errors.rejectValue("soTietTh", "monHoc", "Số tiết thực hành không thể < 0");
        }

        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                errorMessage = error.getDefaultMessage();
                break; // Chỉ lấy lỗi đầu tiên
            }
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/subject";
        }

        monHoc.setTrangThaiXoa(false);
        int statusThemMonHoc = monHocService.addMonHoc(monHoc);
        redirectAttributes.addFlashAttribute("statusThemMonHoc", statusThemMonHoc);
        return "redirect:/admin/subject";
    }

    // @PostMapping("/editMonHoc")
    // public String editMonHoc(@RequestParam("mamh") String mamh,
    // @RequestParam("tenmh") String tenmh,
    // @RequestParam("soTietLt") int soTietLt,
    // @RequestParam("soTietTh") int soTietTh, RedirectAttributes
    // redirectAttributes) {
    // int statusEditMonHoc = monHocService.editMonHoc(mamh, tenmh, soTietLt,
    // soTietTh);
    // redirectAttributes.addFlashAttribute("statusThemMonHoc", statusEditMonHoc);
    // return "redirect:/admin/subject";
    // }

    @PostMapping("/editMonHoc")
    public String editMonHoc(@ModelAttribute("monHoc") MonHoc monHoc, RedirectAttributes redirectAttributes,
            BindingResult errors) {
        String errorMessage = "";
        if (monHoc.getMamh().trim().length() == 0) {
            errors.rejectValue("mamh", "monHoc", "Mã môn học không được bỏ trống");
        }
        if (monHoc.getTenmh().trim().length() == 0) {
            errors.rejectValue("tenmh", "monHoc", "Tên môn học không được bỏ trống");
        }
        if (monHoc.getSoTietLt() < 0) {
            errors.rejectValue("soTietLt", "monHoc", "Số tiết lý thuyết không thể < 0");
        }
        if (monHoc.getSoTietTh() < 0) {
            errors.rejectValue("soTietTh", "monHoc", "Số tiết thực hành không thể < 0");
        }
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                errorMessage = error.getDefaultMessage();
                break; // Chỉ lấy lỗi đầu tiên
            }
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/subject";
        }

        int statusEditMonHoc = monHocService.editMonHoc(monHoc.getMamh(), monHoc.getTenmh(), monHoc.getSoTietLt(),
                monHoc.getSoTietTh());
        redirectAttributes.addFlashAttribute("statusThemMonHoc", statusEditMonHoc);
        return "redirect:/admin/subject";
    }

    @GetMapping("/searchMonHoc")
    public String searchMonHoc(@RequestParam(name = "keyword") String keyword, ModelMap model) {
        List<MonHoc> monHocList = monHocService.searchMonHoc(keyword);
        model.addAttribute("monHocList", monHocList);
        return "/admin/subject";
    }

}
