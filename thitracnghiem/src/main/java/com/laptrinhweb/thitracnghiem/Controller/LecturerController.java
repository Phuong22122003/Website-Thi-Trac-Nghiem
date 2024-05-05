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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Service.DangKyThiService;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {
    @Autowired
    GiangVienService giangVienService;
    @Autowired
    DangKyThiService dangKyThiService;

    @ModelAttribute("giangVien")
    public GiangVien giangVienLogin() {
        return giangVienService.findByMaGv("gv02           ");
    }

    @GetMapping("/question")
    public String getQuestionLisString(ModelMap model) {
        return "/lecturer/question";
    }

    @GetMapping("/score")
    public String getScore(ModelMap model, @ModelAttribute("giangVien") GiangVien giangVien) {
        List<DangKyThiDTO> list = giangVienService.findDangKyThiByGiangVien(giangVien.getMaGv());
        model.addAttribute("dangKyThiList",
                list);
        return "/lecturer/score";
    }

    @GetMapping("/getListScore/{iddk}")
    public String getListScore(@PathVariable("iddk") int iddk, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("listScore", giangVienService.findThiFromIddk(iddk));
        return "redirect:/lecturer/score";
    }

    @GetMapping("/profile")
    public String getProfile() {

        return "/lecturer/profile";
    }

    @PostMapping("/profile")
    public String changeProfile(@ModelAttribute("giangVien") GiangVien giangVien, ModelMap model,
            BindingResult errors) {
        if (giangVien.getHo().length() == 0)
            errors.rejectValue("ho", "giangVien", "Họ không được để trống!");
        if (giangVien.getTen().length() == 0)
            errors.rejectValue("ten", "giangVien", "Tên không được để trống!");
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                System.out.println("====================================");
                System.out.println(error.getField());
                model.addAttribute("errorProfile_" + error.getField(), error.getDefaultMessage());
            }
            return "/lecturer/profile";
        }
        giangVienService.changeProfile(giangVien);
        // redirectAttributes.addFlashAttribute("statusChangeProfile", 1);
        model.addAttribute("statusChangeProfile", 1);
        return "lecturer/profile";
    }

    @GetMapping("/searchScoreList/{maGv}")
    public String getMethodName(@RequestParam(name = "keyword") String keyword, @PathVariable("maGv") String maGv,
            ModelMap model) {
        System.out.println("=================voday==================");
        model.addAttribute("dangKyThiList", dangKyThiService.searchDangKyThi(keyword,
                maGv));
        return "lecturer/score";
    }

}
