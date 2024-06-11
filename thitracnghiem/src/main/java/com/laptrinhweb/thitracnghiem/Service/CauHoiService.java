package com.laptrinhweb.thitracnghiem.Service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laptrinhweb.thitracnghiem.DTO.CauHoiDTO;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.DTO.InfoFileDTO;
import com.laptrinhweb.thitracnghiem.DTO.LuaChonDTO;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.FileCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.FileRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.GiangVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LuaChonRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.MonHocRepository;

import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.time.LocalDateTime;

@Service
public class CauHoiService {
    @Autowired
    private CauHoiRepository cauHoiRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private LuaChonRepository luaChonRepository;
    @Autowired
    private MonHocService monHocService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired 
    private SessionFactory sessionFactory;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<CauHoiThiDTO> getListOfExamQuestion(Integer idThi, List<Integer> dap_an) {
        List<CauHoiThiDTO> li =null;
        String spCall = "exec listOfExamQuestions :idThi";
        Session session = sessionFactory.openSession();
        Query<CauHoiThiDTO> query = session.createNativeQuery(spCall, CauHoiThiDTO.class);
        query.setParameter("idThi",idThi);
        li=query.getResultList();
        for(CauHoiThiDTO ch:li ){
            dap_an.add(ch.getDapAnDung());
            ch.setDapAnDung(null);
            ch.setLuaChons(luaChonRepository.findAllLuaChonByIdch(ch.getIdch()));
            ch.setFiles(fileRepository.findAllByIDCH(ch.getIdch()));
        }
        session.close();
        return li;
    }

    public DanhSachCauHoi getPastExamQuestions(Integer idThi) {
        DanhSachCauHoi ds = new DanhSachCauHoi();
        List<CauHoiThiDTO> li = new ArrayList<>();
        CauHoiThiDTO temp;
        List<Object[]> rs ;
        List<LuaChon> luaChons = null;
        List<FileCauHoi> files = null;
        CauHoi cauHoi = null;
        String spCall = "exec getPastExamQuestions  :idThi";
        Session session = sessionFactory.openSession();
        Query<Object[]> query = session.createNativeQuery(spCall, Object[].class);
        query.setParameter("idThi",idThi);
        rs = query.getResultList();
        for(Object[] r: rs){
            temp = new CauHoiThiDTO((Integer)r[0], r[1].toString(), (Integer)r[2],(Integer)r[3],(Integer)r[4]);
            cauHoi = cauHoiRepository.findByIdch((Integer)r[0]);
            if(cauHoi!=null){
                luaChons = (List<LuaChon>) cauHoi.getLuaChons();
                files = (List<FileCauHoi>) cauHoi.getFiles();
                temp.setLuaChons(luaChons);
                if(files!=null)temp.setFiles(files);
                li.add(temp);
            }
        }
        ds.setListCauHoi(li);      
        session.close();
        return ds;
    }

    public List<CauHoiDTO> findCauHoiByMaGv(String maGv) {
        return cauHoiRepository.findCauHoiByMaGv(maGv);
    }

    public List<CauHoiDTO> searchCauHoi(String keyword, String maGv) {
        return cauHoiRepository.searchCauHoi(keyword, maGv);
    }

    public List<FileCauHoi> findFileCauHoiByCauHoi(CauHoi cauHoi) {
        return fileRepository.findByCauHoi(cauHoi);
    }

    public List<InfoFileDTO> findInfoFile(CauHoi cauHoi) {
        return fileRepository.findInfoFile(cauHoi.getIdch());
    }

    @Transactional(rollbackOn = Exception.class)
    public int createYesNoQuestion(String noiDungCauhoi, String maMh, String maGv, int dapAnDung, MultipartFile audio,
            MultipartFile image) {
        try {

            CauHoi cauHoi = new CauHoi();
            cauHoi.setDapAnDung(dapAnDung);
            cauHoi.setGiangVien(giangVienRepository.findByMaGvAndTrangThaiXoa(maGv, false));
            cauHoi.setMonHoc(monHocRepository.findByMamh(maMh));
            cauHoi.setNoiDung(noiDungCauhoi);
            cauHoi.setTrangThaiXoa(false);
            cauHoi.setHinhThuc("YES/NO");
            cauHoi = cauHoiRepository.save(cauHoi);
            LuaChon luaChonYes = new LuaChon();
            luaChonYes.setCauHoi(cauHoi);
            luaChonYes.setNoiDung("Yes");
            luaChonYes.setThuTu(1);
            luaChonYes.setTrangThaiXoa(false);
            luaChonRepository.save(luaChonYes);
            LuaChon luaChonNo = new LuaChon();
            luaChonNo.setCauHoi(cauHoi);
            luaChonNo.setNoiDung("No");
            luaChonNo.setThuTu(2);
            luaChonNo.setTrangThaiXoa(false);
            luaChonRepository.save(luaChonNo);
            if (audio.getOriginalFilename() != "") {
                long time = System.currentTimeMillis();
                FileCauHoi fileAudio = new FileCauHoi();
                fileAudio.setCauHoi(cauHoi);
                fileAudio.setFileName(time +
                        audio.getOriginalFilename());
                // fileAudio.setFileName("a");
                fileAudio.setType("audio");
                fileRepository.save(fileAudio);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(time + audio.getOriginalFilename());
                Files.write(filePath, audio.getBytes());
            }
            if (image.getOriginalFilename() != "") {
                long time = System.currentTimeMillis();
                FileCauHoi fileImage = new FileCauHoi();
                fileImage.setCauHoi(cauHoi);
                fileImage.setFileName(time + image.getOriginalFilename());
                fileImage.setType("image");
                fileRepository.save(fileImage);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(time + image.getOriginalFilename());
                Files.write(filePath, image.getBytes());
            }

            return 0;
        } catch (Exception e) {
            System.out.println("=============================");
            System.out.println(e.toString());
            return 1;
            // TODO: handle exception
        }

    }

    // @Transactional(rollbackOn = Exception.class)
    public int createOthersQuestion(String noiDungCauhoi, String maMh, String maGv, int dapAnDung,
            List<String> luaChonList, String hinhThuc, MultipartFile audio,
            MultipartFile image) {
        try {
            System.out.println("===========");
            System.out.println(maGv);
            CauHoi cauHoi = new CauHoi();
            cauHoi.setDapAnDung(dapAnDung);
            cauHoi.setGiangVien(giangVienRepository.findByMaGvAndTrangThaiXoa(maGv, false));
            cauHoi.setMonHoc(monHocRepository.findByMamh(maMh));
            cauHoi.setNoiDung(noiDungCauhoi);
            cauHoi.setTrangThaiXoa(false);
            cauHoi.setHinhThuc(hinhThuc);
            cauHoi = cauHoiRepository.save(cauHoi);
            int thutu = 1;
            for (String noiDungLuaChon : luaChonList) {
                LuaChon luaChon = new LuaChon();
                luaChon.setCauHoi(cauHoi);
                luaChon.setNoiDung(noiDungLuaChon);
                luaChon.setThuTu(thutu++);
                luaChon.setTrangThaiXoa(false);
                luaChonRepository.save(luaChon);
            }
            if (audio.getOriginalFilename() != "") {
                long time = System.currentTimeMillis();
                FileCauHoi fileAudio = new FileCauHoi();
                fileAudio.setCauHoi(cauHoi);
                fileAudio.setFileName(time +
                        audio.getOriginalFilename());
                // fileAudio.setFileName("a");
                fileAudio.setType("audio");
                fileRepository.save(fileAudio);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(time + audio.getOriginalFilename());
                Files.write(filePath, audio.getBytes());
            }
            if (image.getOriginalFilename() != "") {
                long time = System.currentTimeMillis();
                FileCauHoi fileImage = new FileCauHoi();
                fileImage.setCauHoi(cauHoi);
                fileImage.setFileName(time + image.getOriginalFilename());
                fileImage.setType("image");
                fileRepository.save(fileImage);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(time + image.getOriginalFilename());
                Files.write(filePath, image.getBytes());
            }
            return 0;
        } catch (Exception e) {
            System.out.println("================================");
            System.out.println(e.toString());
            return 1;
        }
    }

    // @Transactional(rollbackOn = Exception.class)
    public int deleteQuestion(int idch) {
        try {
            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);
            if (cauHoi.getCtBaiThis().size() > 0) {
                return 1;
            }
            List<LuaChon> luaChons = new ArrayList(cauHoi.getLuaChons());
            if (luaChons != null && luaChons.size() > 0) {
                for (LuaChon luaChon : luaChons) {
                    luaChon.setTrangThaiXoa(true);
                    luaChonRepository.save(luaChon);
                }
            }
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            for (FileCauHoi fileCauHoi : cauHoi.getFiles()) {
                Path deletedPath = uploadPath.resolve(fileCauHoi.getFileName());
                Files.deleteIfExists(deletedPath);
            }
            cauHoi.getFiles().clear();
            cauHoi.setTrangThaiXoa(true);
            cauHoiRepository.save(cauHoi);
            return 0;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("=====================");
            System.out.println(e.toString());
            return 1;
        }
    }

    public List<LuaChonDTO> getInfoLuaChon(int idch) {
        List<LuaChonDTO> luaChonDTOList = luaChonRepository.findLuaChonByIdch(idch);
        return luaChonDTOList;
    }

    // @Transactional(rollbackOn = Exception.class)
    // public int editYesNoQuestion(String mamh, String noiDungCauHoi, Integer
    // dapAnDung, Integer idch,
    // MultipartFile image, MultipartFile audio) {

    // try {
    // if (noiDungCauHoi.length() == 0 || monHocService.findByMamh(mamh) == null ||
    // dapAnDung == null
    // || idch == null)
    // return 1;
    // Path uploadPath = Paths.get(uploadDir);
    // CauHoi cauHoi = cauHoiRepository.findByIdch(idch);
    // cauHoi.setMonHoc(monHocRepository.findByMamh(mamh));
    // cauHoi.setNoiDung(noiDungCauHoi);
    // cauHoi.setDapAnDung(dapAnDung);

    // List<FileCauHoi> currentFiles = new ArrayList<>(cauHoi.getFiles());
    // if (image.getOriginalFilename() != "" || audio.getOriginalFilename() != "") {
    // for (FileCauHoi fileCauHoi : currentFiles) {
    // String type = null;
    // String name = null;
    // long time = System.currentTimeMillis();
    // boolean isChanged = false;
    // if (image.getOriginalFilename() != "" &&
    // fileCauHoi.getType().equals("image")) {
    // type = "image";
    // currentFiles.remove(fileCauHoi);
    // name = time + image.getOriginalFilename();
    // isChanged = true;
    // }
    // if (audio.getOriginalFilename() != "" &&
    // fileCauHoi.getType().equals("audio")) {
    // type = "audio";
    // currentFiles.remove(fileCauHoi);
    // name = time + audio.getOriginalFilename();
    // isChanged = true;
    // }
    // if (isChanged) {
    // Path deletePath = uploadPath.resolve(fileCauHoi.getFileName());
    // Files.deleteIfExists(deletePath);
    // FileCauHoi newFileCauHoi = new FileCauHoi();
    // newFileCauHoi.setCauHoi(cauHoi);
    // newFileCauHoi.setFileName(time + image.getOriginalFilename());
    // newFileCauHoi.setType(type);
    // currentFiles.add(newFileCauHoi);
    // if (!Files.exists(uploadPath)) {
    // Files.createDirectories(uploadPath);
    // }
    // Path filePath = uploadPath.resolve(name);
    // Files.write(filePath, image.getBytes());
    // }
    // }
    // }

    // cauHoi.setFiles(currentFiles);
    // cauHoiRepository.save(cauHoi);
    // return 0;
    // } catch (

    // Exception e) {
    // System.out.println("====================");
    // System.out.println(e.toString());
    // return 1;
    // }
    // }

    // public static boolean containsEmptyString(List<String> list) {
    // for (String str : list) {
    // if (str.isEmpty()) {
    // return false; // Trả về false nếu có chuỗi rỗng
    // }
    // }
    // return true; // Trả về true nếu không có chuỗi rỗng
    // }
    @Transactional(rollbackOn = Exception.class)
    public int editYesNoQuestion(String mamh, String noiDungCauHoi, Integer dapAnDung, Integer idch,
            MultipartFile image, MultipartFile audio) {
        try {
            // Validate input parameters
            if (noiDungCauHoi.isEmpty() || monHocService.findByMamh(mamh) == null || dapAnDung == null
                    || idch == null) {
                return 1;
            }

            // Fetch the question by id
            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);
            if (cauHoi == null) {
                return 1;
            }

            // Set new values for the question
            cauHoi.setMonHoc(monHocRepository.findByMamh(mamh));
            cauHoi.setNoiDung(noiDungCauHoi);
            cauHoi.setDapAnDung(dapAnDung);

            // Prepare the upload directory path
            Path uploadPath = Paths.get(uploadDir);

            // Handle image and audio file updates
            if (!image.getOriginalFilename().isEmpty() || !audio.getOriginalFilename().isEmpty()) {
                List<FileCauHoi> updatedFiles = new ArrayList<>(cauHoi.getFiles());

                // Handle image file update
                if (!image.getOriginalFilename().isEmpty()) {
                    handleFileUpdate(image, "image", cauHoi, updatedFiles, uploadPath);
                }

                // Handle audio file update
                if (!audio.getOriginalFilename().isEmpty()) {
                    handleFileUpdate(audio, "audio", cauHoi, updatedFiles, uploadPath);
                }

                // Update the files list using utility methods
                cauHoi.getFiles().clear();
                for (FileCauHoi file : updatedFiles) {
                    cauHoi.addFile(file);
                }
                // cauHoi.setFiles(updatedFiles);
            }

            // Save the updated question
            cauHoiRepository.save(cauHoi);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private void handleFileUpdate(MultipartFile file, String fileType, CauHoi cauHoi, List<FileCauHoi> files,
            Path uploadPath) throws IOException {
        // Generate new file name
        long time = System.currentTimeMillis();
        String newFileName = time + "_" + file.getOriginalFilename(); // Make sure the file name is unique

        // Remove existing file of the same type
        files.removeIf(f -> f.getType().equals(fileType));

        // Delete the existing file if it exists
        for (FileCauHoi existingFile : files) {
            if (existingFile.getType().equals(fileType)) {
                Path deletePath = uploadPath.resolve(existingFile.getFileName());
                Files.deleteIfExists(deletePath);
            }
        }

        // Create new file entry
        FileCauHoi newFileCauHoi = new FileCauHoi();
        newFileCauHoi.setCauHoi(cauHoi);
        newFileCauHoi.setFileName(newFileName);
        newFileCauHoi.setType(fileType);
        files.add(newFileCauHoi);

        // Write new file to the disk
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(newFileName);
        Files.write(filePath, file.getBytes());
    }

    @Transactional(rollbackOn = Exception.class)
    public int editOthersQuestion(String mamh, String noiDungCauHoi, Integer dapAnDung, Integer idch,
            List<Integer> idlcEditedList,
            List<String> noiDungLuaChonEditedList, List<String> noiDungLuaChonAddedList, MultipartFile image,
            MultipartFile audio) {
        try {
            if (noiDungCauHoi.length() == 0 || monHocService.findByMamh(mamh) == null || dapAnDung == null
                    || idch == null || idlcEditedList.size() == 0 || noiDungLuaChonEditedList.size() == 0)
                return 1;

            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);

            cauHoi.setMonHoc(monHocRepository.findByMamh(mamh));
            cauHoi.setNoiDung(noiDungCauHoi);
            cauHoi.setDapAnDung(dapAnDung);
            Path uploadPath = Paths.get(uploadDir);

            // Handle image and audio file updates
            if (!image.getOriginalFilename().isEmpty() || !audio.getOriginalFilename().isEmpty()) {
                List<FileCauHoi> updatedFiles = new ArrayList<>(cauHoi.getFiles());

                // Handle image file update
                if (!image.getOriginalFilename().isEmpty()) {
                    handleFileUpdate(image, "image", cauHoi, updatedFiles, uploadPath);
                }

                // Handle audio file update
                if (!audio.getOriginalFilename().isEmpty()) {
                    handleFileUpdate(audio, "audio", cauHoi, updatedFiles, uploadPath);
                }

                // Update the files list using utility methods
                cauHoi.getFiles().clear();
                for (FileCauHoi file : updatedFiles) {
                    cauHoi.addFile(file);
                }
                // cauHoi.setFiles(updatedFiles);
            }
            cauHoiRepository.save(cauHoi);
            int thuTu = noiDungLuaChonEditedList.size() + 1;
            int i = 0;
            for (int idlc : idlcEditedList) {
                LuaChon luaChon = luaChonRepository.findByIdlc(idlc);
                luaChon.setNoiDung(noiDungLuaChonEditedList.get(i++));
                luaChonRepository.save(luaChon);
            }
            if (noiDungLuaChonAddedList.size() > 0) {
                for (String noiDungLuaChon : noiDungLuaChonAddedList) {
                    LuaChon luaChon = new LuaChon();
                    luaChon.setCauHoi(cauHoi);
                    luaChon.setNoiDung(noiDungLuaChon);
                    luaChon.setThuTu(thuTu++);
                    luaChonRepository.save(luaChon);
                }
            }

        } catch (Exception e) {
            System.out.println("====================");
            System.out.println(e.toString());
            return 1;
        }
        return 0;
    }

    public CauHoi findByIdch(int idch) {
        return cauHoiRepository.findByIdch(idch);
    }

    public CauHoi findByIdlc(int idlc) {
        LuaChon luaChon = luaChonRepository.findByIdlc(idlc);
        if (luaChon != null) {
            return luaChon.getCauHoi();
        }
        return null;
    }

    public int deleteLuaChon(int idlc) {
        try {
            LuaChon deletedLuaChon = luaChonRepository.findByIdlc(idlc);
            LuaChon luaChonDung = null;
            CauHoi cauHoi = deletedLuaChon.getCauHoi();
            if (cauHoi.getLuaChons().size() <= 1)
                return 2;
            for (LuaChon luaChon : cauHoi.getLuaChons()) {
                if (luaChon.getThuTu() == cauHoi.getDapAnDung()) {
                    luaChonDung = luaChon;
                }
            }

            int thutu = 1;

            for (LuaChon lc : cauHoi.getLuaChons()) {
                if (lc.getIdlc() != deletedLuaChon.getIdlc()) {
                    lc.setThuTu(thutu++);
                    luaChonRepository.save(lc);
                }
            }

            if (luaChonDung.getIdlc() == deletedLuaChon.getIdlc()) {
                cauHoi.setDapAnDung(1);
            } else {
                cauHoi.setDapAnDung(luaChonDung.getThuTu());
            }
            luaChonRepository.delete(deletedLuaChon);
            return 0;
        } catch (Exception e) {
            System.out.println("===========================");
            System.out.println(e.toString());
            return 1;
        }
    }
}
