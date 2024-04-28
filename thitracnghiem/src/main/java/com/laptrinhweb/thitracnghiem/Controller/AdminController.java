package com.laptrinhweb.thitracnghiem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import com.laptrinhweb.thitracnghiem.Service.MonHocService;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    MonHocService monHocService;
    @Autowired
    GiangVienService giangVienService;

    @GetMapping("/student")
    public String student() {
        return "/admin/student";
    }

    @GetMapping("/class")
    public String lophoc() {
        return "/admin/class";
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

    @PostMapping("/addMonHoc")
    public String addMonHoc(@Valid @ModelAttribute("monHoc") MonHoc monHoc,
            RedirectAttributes redirectAttributes, BindingResult errors) {
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
            redirectAttributes.addFlashAttribute("errorAdd", true);
            for (FieldError error : errors.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(), error.getDefaultMessage());
            }
            return "redirect:/admin/subject";
        }

        monHoc.setTrangThaiXoa(false);
        int statusThemMonHoc = monHocService.addMonHoc(monHoc);
        redirectAttributes.addFlashAttribute("statusThemMonHoc", statusThemMonHoc);
        return "redirect:/admin/subject";
    }

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
                redirectAttributes.addFlashAttribute("errorEdit", error.getDefaultMessage());
                return "redirect:/admin/subject";
                // redirectAttributes.addFlashAttribute("errorEdit_" + error.getField(),
                // error.getDefaultMessage());
            }

        }

        int statusEditMonHoc = monHocService.editMonHoc(monHoc.getMamh(), monHoc.getTenmh(), monHoc.getSoTietLt(),
                monHoc.getSoTietTh());
        redirectAttributes.addFlashAttribute("statusEditMonHoc", statusEditMonHoc);
        return "redirect:/admin/subject";
    }

    @GetMapping("/searchMonHoc")
    public String searchMonHoc(@RequestParam(name = "keyword") String keyword, ModelMap model) {
        List<MonHoc> monHocList = monHocService.searchMonHoc(keyword);
        model.addAttribute("monHocList", monHocList);
        return "/admin/subject";
    }

    // =============================Giang Vien=============================
    @ModelAttribute("giangVien")
    public GiangVien createNewGiangVien() {
        return new GiangVien();
    }

    @GetMapping("/lecturer")
    public String lecturer(ModelMap model) {
        List<GiangVien> giangVienList = giangVienService.findAllGiangVien();
        model.addAttribute("giangVienList", giangVienList);
        return "admin/lecturer";
    }

    @GetMapping("/deleteGiangVien/{maGv}")
    public String getMethodName(@PathVariable("maGv") String maGv, RedirectAttributes redirectAttributes) {
        int statusXoaGiangVien = giangVienService.deleteByMaGv(maGv);
        redirectAttributes.addFlashAttribute("statusXoaGiangVien", statusXoaGiangVien);
        return "redirect:/admin/lecturer";
    }

    @PostMapping("/addGiangVien")
    public String addGiangVien(@ModelAttribute("giangVien") GiangVien giangVien, RedirectAttributes redirectAttributes,
            BindingResult errors) {
        if (giangVien.getHo().trim().length() == 0) {
            errors.rejectValue("ho", "giangVien", "Họ giảng viên không được bỏ trống");
        }
        if (giangVien.getMaGv().trim().length() == 0) {
            errors.rejectValue("maGv", "giangVien", "Mã giảng viên không được bỏ trống");
        }
        if (giangVien.getPassWord().trim().length() == 0) {
            errors.rejectValue("passWord", "giangVien", "Password không được bỏ trống");
        }
        if (giangVien.getTen().trim().length() == 0) {
            errors.rejectValue("ten", "giangVien", "Tên giảng viên không được bỏ trống");
        }
        if (giangVien.getUserName().trim().length() == 0) {
            errors.rejectValue("userName", "giangVien", "Username không được bỏ trống");
        }
        if (giangVien.getEmail().trim().length() == 0) {
            errors.rejectValue("email", "giangVien", "Email không được bỏ trống");
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdd", true);
            for (FieldError error : errors.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(), error.getDefaultMessage());
            }
            return "redirect:/admin/lecturer";
        }

        giangVien.setTrangThaiXoa(false);
        int statusThemGiangVien = giangVienService.addGiangVien(giangVien);
        System.out.println("================================================");
        System.out.println(statusThemGiangVien);
        redirectAttributes.addFlashAttribute("statusThemGiangVien", statusThemGiangVien);
        return "redirect:/admin/lecturer";
    }

    @PostMapping("/editGiangVien")
    public String editGiangVien(@ModelAttribute("giangVien") GiangVien giangVien, RedirectAttributes redirectAttributes,
            BindingResult errors) {
        if (giangVien.getHo().trim().length() == 0) {
            errors.rejectValue("ho", "giangVien", "Họ giảng viên không được bỏ trống");
        }
        if (giangVien.getMaGv().trim().length() == 0) {
            errors.rejectValue("maGv", "giangVien", "Mã giảng viên không được bỏ trống");
        }
        if (giangVien.getTen().trim().length() == 0) {
            errors.rejectValue("ten", "giangVien", "Tên giảng viên không được bỏ trống");
        }
        if (giangVien.getEmail().trim().length() == 0) {
            errors.rejectValue("email", "giangVien", "Email không được bỏ trống");
        }
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("errorEdit", error.getDefaultMessage());
                return "redirect:/admin/lecturer";
                // redirectAttributes.addFlashAttribute("errorEdit_" + error.getField(),
                // error.getDefaultMessage());
            }
            return "redirect:/admin/lecturer";
        }

        giangVien.setTrangThaiXoa(false);
        int statusEditGiangVien = giangVienService.editGiangVien(giangVien.getHo(), giangVien.getTen(),
                giangVien.getEmail(), giangVien.getHocVi(), giangVien.getHocHam(), giangVien.isGioiTinh(),
                giangVien.getMaGv());
        redirectAttributes.addFlashAttribute("statusEditGiangVien", statusEditGiangVien);
        return "redirect:/admin/lecturer";
    }

    @GetMapping("/searchGiangVien")
    public String searchGiangVien(@RequestParam(name = "keyword") String keyword, ModelMap model) {
        List<GiangVien> giangVienList = giangVienService.searchGiangVien(keyword);
        model.addAttribute("giangVienList", giangVienList);
        return "admin/lecturer";
    }

}
