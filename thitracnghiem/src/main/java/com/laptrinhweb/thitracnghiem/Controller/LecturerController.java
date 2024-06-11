package com.laptrinhweb.thitracnghiem.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.InfoFileDTO;
import com.laptrinhweb.thitracnghiem.DTO.LuaChonDTO;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.ChangePasswordForm;
import com.laptrinhweb.thitracnghiem.Entity.FileCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Service.CauHoiService;
import com.laptrinhweb.thitracnghiem.Service.DangKyThiService;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import com.laptrinhweb.thitracnghiem.Service.MonHocService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    GiangVienService giangVienService;
    @Autowired
    DangKyThiService dangKyThiService;
    @Autowired
    MonHocService monHocService;
    @Autowired
    CauHoiService cauHoiService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("giangVien")
    public GiangVien giangVienLogin(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String magv = giangVienService.findByUsername(username).getMaGv();
        return giangVienService.findByMaGv(magv);
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
    public String changeProfile(@Validated @ModelAttribute("giangVien") GiangVien giangVien,
            BindingResult errors, ModelMap model) {
        if (giangVien.getHo().length() == 0)
            errors.rejectValue("ho", "giangVien", "Họ không được để trống!");
        if (giangVien.getTen().length() == 0)
            errors.rejectValue("ten", "giangVien", "Tên không được để trống!");
        if (errors.hasErrors()) {
            // for (FieldError error : errors.getFieldErrors()) {
            // System.out.println("====================================");
            // System.out.println(error.getField());
            // model.addAttribute("errorProfile_" + error.getField(),
            // error.getDefaultMessage());
            // }
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

        model.addAttribute("dangKyThiList", dangKyThiService.searchDangKyThi(keyword,
                maGv));
        return "lecturer/score";
    }

    @GetMapping("/question")
    public String getQuestion(ModelMap model, @ModelAttribute("giangVien") GiangVien giangVien) {
        model.addAttribute("monHocList", monHocService.getAllMonHoc());
        model.addAttribute("questionList",
                cauHoiService.findCauHoiByMaGv(giangVien.getMaGv()));
        return "lecturer/question";
    }

    @GetMapping("/searchQuestion/{maGv}")
    public String searchQuestioString(@RequestParam(name = "keyword") String keyword, @PathVariable("maGv") String maGv,
            ModelMap model) {
        List<CauHoiDTO> list = cauHoiService.searchCauHoi(keyword,
                maGv);
        if (list.size() == 0) {
            return "redirect:/lecturer/question";
        }
        model.addAttribute("questionList", list);
        model.addAttribute("monHocList", monHocService.getAllMonHoc());
        return "lecturer/question";
    }

    @PostMapping("/create-yesno-question/{maGv}")
    public String createYesNoQuestion(@RequestParam("yes/no") Integer dapAnDung,
            @RequestParam("noiDungCauHoi") String noiDungCauHoi,
            @RequestParam("monHoc") String maMh, @PathVariable("maGv") String maGv,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "audio", required = false) MultipartFile audio,
            RedirectAttributes redirectAttributes) {
        int statusCreateCauHoi;
        if (dapAnDung == null || noiDungCauHoi.trim().length() == 0 || maMh.trim().length() == 0
                || maGv.trim().length() == 0
                || monHocService.findByMamh(maMh) == null || giangVienService.findByMaGv(maGv) == null) {
            statusCreateCauHoi = 1;
            redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
            redirectAttributes.addFlashAttribute("message", "Thông tin không hợp lệ");
            return "redirect:/lecturer/question";
        }
        try {

            // Lưu tệp vào hệ thống tệp
            if (image.getOriginalFilename() != "" && !image.getContentType().contains("image")) {
                statusCreateCauHoi = 1;
                redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
                redirectAttributes.addFlashAttribute("message", "Hình ảnh không hợp lệ");
                return "redirect:/lecturer/question";
            }
            if (audio.getOriginalFilename() != "" && !audio.getContentType().contains(("audio"))) {
                statusCreateCauHoi = 1;
                redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
                redirectAttributes.addFlashAttribute("message", "Audio không hợp lệ");
                return "redirect:/lecturer/question";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '");
        }
        statusCreateCauHoi = cauHoiService.createYesNoQuestion(noiDungCauHoi,
                maMh, maGv, dapAnDung, audio, image);
        redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
        return "redirect:/lecturer/question";
    }

    @PostMapping("/create-others-question/{maGv}")
    public String createOthersQuestion(@RequestParam("monHoc") String maMh, @RequestParam("hinhThuc") String hinhThuc,
            @RequestParam("noiDungCauHoi") String noiDungCauHoi, @RequestParam("luachon") List<String> luaChonList,
            @RequestParam(name = "dapAnDung", required = false) Integer dapAnDung, @PathVariable("maGv") String maGv,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "audio", required = false) MultipartFile audio,
            RedirectAttributes redirectAttributes) {
        int statusCreateCauHoi;
        if (dapAnDung == null || noiDungCauHoi.trim().length() == 0 || maMh.trim().length() == 0
                || maGv.trim().length() == 0
                || monHocService.findByMamh(maMh) == null || giangVienService.findByMaGv(maGv) == null
                || luaChonList.size() == 0 || hinhThuc.length() == 0) {
            statusCreateCauHoi = 1;
            redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
            redirectAttributes.addFlashAttribute("message", "Thông tin không hợp lệ");
            return "redirect:/lecturer/question";
        }
        try {

            // Lưu tệp vào hệ thống tệp
            if (image.getOriginalFilename() != "" && !image.getContentType().contains("image")) {
                statusCreateCauHoi = 1;
                redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
                redirectAttributes.addFlashAttribute("message", "Hình ảnh không hợp lệ");
                return "redirect:/lecturer/question";
            }
            if (audio.getOriginalFilename() != "" && !audio.getContentType().contains(("audio"))) {
                statusCreateCauHoi = 1;
                redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
                redirectAttributes.addFlashAttribute("message", "Audio không hợp lệ");
                return "redirect:/lecturer/question";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '");
        }
        statusCreateCauHoi = cauHoiService.createOthersQuestion(noiDungCauHoi, maMh, maGv, dapAnDung,
                luaChonList,
                hinhThuc, audio, image);
        redirectAttributes.addFlashAttribute("statusCreateCauHoi", statusCreateCauHoi);
        return "redirect:/lecturer/question";
    }

    @GetMapping("/delete-question/{idch}")
    public String getMethodName(@PathVariable("idch") int idch, RedirectAttributes redirectAttributes) {
        int statusDeleteCauHoi = cauHoiService.deleteQuestion(idch);
        redirectAttributes.addFlashAttribute("statusDeleteCauHoi", statusDeleteCauHoi);
        return "redirect:/lecturer/question";
    }

    @GetMapping("/get-info-luachon/{idch}")
    public String getInfoLuaChon(@PathVariable("idch") int idch,
            RedirectAttributes redirectAttributes) {
        CauHoi cauHoi = cauHoiService.findByIdch(idch);
        if (cauHoi.getCtBaiThis().size() > 0) {
            redirectAttributes.addFlashAttribute("statusEditCauHoi", 1);
            redirectAttributes.addFlashAttribute("message", "Câu hỏi đã thi không thể sửa");
            return "redirect:/lecturer/question";
        }
        List<InfoFileDTO> fileCauHois = cauHoiService.findInfoFile(cauHoi);
        System.out.println(fileCauHois);
        List<LuaChonDTO> list = cauHoiService.getInfoLuaChon(idch);
        redirectAttributes.addFlashAttribute("listLuaChon", list);
        redirectAttributes.addFlashAttribute("listHinhAnh", fileCauHois);
        redirectAttributes.addFlashAttribute("idchEdited", idch);
        return "redirect:/lecturer/question";
    }

    @GetMapping("/view-info-question/{idch}")
    public String viewInfoQuestion(@PathVariable("idch") int idch, RedirectAttributes redirectAttributes) {
        CauHoi cauHoi = cauHoiService.findByIdch(idch);
        List<LuaChonDTO> list = cauHoiService.getInfoLuaChon(idch);
        redirectAttributes.addFlashAttribute("infoCauhoi", list);
        List<InfoFileDTO> fileCauHois = cauHoiService.findInfoFile(cauHoi);
        redirectAttributes.addFlashAttribute("listHinhAnh", fileCauHois);
        return "redirect:/lecturer/question";
    }

    @PostMapping("/edit-yesno-question/{idch}")
    public String editYesNoQuestion(@PathVariable("idch") int idch,
            @RequestParam("mamh") String mamh, @RequestParam("noiDungCauHoi") String noiDungCauHoi,
            @RequestParam("dapAnDung") int dapAnDung, RedirectAttributes redirectAttributes,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "audio", required = false) MultipartFile audio) {
        int statusEditCauHoi;
        try {

            // Lưu tệp vào hệ thống tệp
            if (image.getOriginalFilename() != "") {
                if (!image.getContentType().contains("image")) {
                    statusEditCauHoi = 1;
                    redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
                    redirectAttributes.addFlashAttribute("message", "Hình ảnh không hợp lệ");
                    return "redirect:/lecturer/question";
                }
            }
            if (audio.getOriginalFilename() != "") {
                if (!audio.getContentType().contains(("audio"))) {
                    statusEditCauHoi = 1;
                    redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
                    redirectAttributes.addFlashAttribute("message", "Audio không hợp lệ");
                    return "redirect:/lecturer/question";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '");
        }
        statusEditCauHoi = cauHoiService.editYesNoQuestion(mamh, noiDungCauHoi, dapAnDung, idch, image, audio);
        redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
        return "redirect:/lecturer/question";
    }

    @PostMapping("/edit-others-question/{idch}")
    public String editYesNoQuestion(@PathVariable("idch") int idch,
            @RequestParam("mamh") String mamh, @RequestParam("noiDungCauHoi") String noiDungCauHoi,
            @RequestParam("dapAnDung") int dapAnDung,
            @RequestParam(name = "luachonEdited", defaultValue = "") List<String> noiDungLuaChonEditedList,
            @RequestParam(name = "idlcEditQuestionList", defaultValue = "") List<Integer> idlcList,
            @RequestParam(name = "luachonAdded", defaultValue = "") List<String> noiDungLuaChonAdded,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "audio", required = false) MultipartFile audio,
            RedirectAttributes redirectAttributes) {

        int statusEditCauHoi;
        try {

            // Lưu tệp vào hệ thống tệp
            if (image.getOriginalFilename() != "") {
                if (!image.getContentType().contains("image")) {
                    statusEditCauHoi = 1;
                    redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
                    redirectAttributes.addFlashAttribute("message", "Hình ảnh không hợp lệ");
                    return "redirect:/lecturer/question";
                }
            }
            if (audio.getOriginalFilename() != "") {
                if (!audio.getContentType().contains(("audio"))) {
                    statusEditCauHoi = 1;
                    redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
                    redirectAttributes.addFlashAttribute("message", "Audio không hợp lệ");
                    return "redirect:/lecturer/question";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '");
        }
        statusEditCauHoi = cauHoiService.editOthersQuestion(mamh, noiDungCauHoi, dapAnDung, idch,
                idlcList,
                noiDungLuaChonEditedList, noiDungLuaChonAdded, image, audio);
        redirectAttributes.addFlashAttribute("statusEditCauHoi", statusEditCauHoi);
        redirectAttributes.addFlashAttribute("idchEdited", idch);
        return "redirect:/lecturer/question";
    }

    @GetMapping("/delete-luachon/{idch}/{idlc}")
    public String deleteCauHoi(@PathVariable("idlc") int idlc, @PathVariable("idch") int idch,
            RedirectAttributes redirectAttributes) {
        int statusDeleteCauHoi = cauHoiService.deleteLuaChon(idlc);
        redirectAttributes.addFlashAttribute("statusDeleteCauHoi", statusDeleteCauHoi);
        String url = "/lecturer/get-info-luachon/" + cauHoiService.findByIdch(idch).getIdch();
        System.out.println(url);
        // return "redirect:" + url;
        return "redirect:/lecturer/question";
    }

    @GetMapping("/change-password")
    public String changePasswordRender(ModelMap model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "/lecturer/change-password";
    }

    @PostMapping("/change-password/{magv}")
    public String changePassword(@PathVariable("magv") String magv,
            @Validated @ModelAttribute("changePasswordForm") ChangePasswordForm form,
            BindingResult bindingResult, ModelMap model) {
        GiangVien giangVien = giangVienService.findByMaGv(magv);
        // System.out.println("Password real: " + giangVien.getPassWord());
        // System.out.println("Old Password chua ma hoa: " + form.getOldPassword());
        // System.out.println("Old Password ma hoa: " +
        // passwordEncoder.encode(form.getOldPassword()));
        // System.out.println("New Password: " + form.getNewPassword());
        // System.out.println("Conform Password: " + form.getConfirmNewPassword());

        // if (form.getNewPassword().isEmpty()) {
        // bindingResult.rejectValue("newPassword", "errorChangePassword_newPassword",
        // "Mật khẩu mới không được để trống");
        // }
        if (!Objects.equals(form.getNewPassword(), form.getConfirmNewPassword())) {
            bindingResult.rejectValue("confirmNewPassword", "errorChangePassword_confirmNewPassword",
                    "Nhập lại mật khẩu không chính xác");

        }
        // if (!Objects.equals(form.getNewPassword(), form.getConfirmNewPassword())) {
        // bindingResult.rejectValue("confirmNewPassword",
        // "errorChangePassword_confirmNewPassword",
        // "Nhập lại mật khẩu không chính xác");
        // }
        if (giangVien != null) {
            if (!passwordEncoder.matches(form.getOldPassword(), giangVien.getPassWord())) {
                bindingResult.rejectValue("oldPassword", "errorChangePassword_oldPassword",
                        "Mật khẩu không chính xác");
            }
            if (bindingResult.hasErrors()) {
                return "/lecturer/change-password";
            }
        }

        giangVienService.changePassword(form.getNewPassword(), magv);
        return "redirect:/logout";
    }
}
