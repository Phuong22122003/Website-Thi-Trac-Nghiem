package com.laptrinhweb.thitracnghiem.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Collection;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
// @SqlResultSetMapping(
//     name = "getResultByIDMapping",
//     classes = @ConstructorResult(
//         targetClass = InfoDTO.class,
//         columns = {
//             @ColumnResult(name = "TENMH", type = String.class),
//             @ColumnResult(name = "NGAYTHI", type = Date.class),
//             @ColumnResult(name = "LAN", type = Integer.class),
//             @ColumnResult(name = "SOCAU", type = Integer.class),
//             @ColumnResult(name = "THOILUONG", type = Integer.class),
//             @ColumnResult(name = "DIEM", type = Float.class)
//         }
//     )
// )
@Table(name = "SINHVIEN")
public class SinhVien {
    @Id
    @NotBlank(message = "Mã sinh viên không được trống!")
    @Length(max = 15, message = "Mã sinh viên tối đa 15 ký tự")
    @Column(name = "MASV")
    private String masv;
    @NotBlank(message = "Họ không được trống!")
    @Length(max = 30, message = "Họ sinh viên tối đa 30 ký tự")
    @Column(name = "HO")
    private String ho;
    @NotBlank(message = "Tên không được trống!")
    @Length(max = 10, message = "Tên sinh viên tối đa 10 ký tự")
    @Column(name = "TEN")
    private String ten;
    @Column(name = "GIOITINH")
    private boolean gioiTinh;
    @NotBlank(message = "Địa chỉ không được trống!")
    @Length(max = 100, message = "Địa chỉ tối đa 100 ký tự")
    @Column(name = "DIACHI")
    private String diaChi;
    @NotNull(message = "Ngày sinh không được trống!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Ngày sinh không hợp lệ!")
    @Column(name = "NGAYSINH")
    private LocalDate ngaySinh;
    @NotBlank(message = "Username không được trống!")
    @Length(max = 50, message = "Username chỉ tối đa 50 ký tự")
    @Column(name = "USERNAME")
    private String userName;
    @NotBlank(message = "Password không được trống!")
    @Length(max = 30, message = "Password chỉ tối đa 30 ký tự")
    @Column(name = "PASSWORD")
    private String passWord;
    @Email(message = "Email không hợp lệ!")
    @Length(max = 50, message = "Email chỉ tối đa 50 ký tự")
    @NotBlank(message = "Email không được trống!")
    @Column(name = "EMAIL")
    private String email;
    // @Column(name = "MALOP")
    // private String maLop;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @ManyToOne
    @JoinColumn(name = "MALOP")
    private Lop lop;
    @OneToMany(mappedBy = "sinhVien", fetch = FetchType.EAGER)
    private Collection<Thi> _this;

    public SinhVien() {
    }

    public SinhVien(String masv, String ho, String ten, boolean gioiTinh, String diaChi, LocalDate ngaySinh, String userName,
            String passWord, String maLop) {
        this.masv = masv;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.userName = userName;
        this.passWord = passWord;
        // this.maLop = maLop;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Collection<Thi> get_this() {
        return _this;
    }

    public void set_this(Collection<Thi> _this) {
        this._this = _this;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // public String getMaLop() {
    // return maLop;
    // }

    // public void setMaLop(String maLop) {
    // this.maLop = maLop;
    // }

}
