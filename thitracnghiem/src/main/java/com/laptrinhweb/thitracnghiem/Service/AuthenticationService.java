package com.laptrinhweb.thitracnghiem.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Implement.GiangVienRepositoryImplt;
import com.laptrinhweb.thitracnghiem.Repository.Implement.NhanVienRepositoryImplt;
import com.laptrinhweb.thitracnghiem.Repository.Implement.SinhVienRepositoryImplt;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private SinhVienRepositoryImplt sinhVienRepositoryImplt;
    @Autowired
    private GiangVienRepositoryImplt giangVienRepositoryImplt;
    @Autowired
    private NhanVienRepositoryImplt nhanVienRepositoryImplt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        boolean check = false;
        Object user = null;
        String password = "";
        if ((user = sinhVienRepositoryImplt.getStudentByUserName(username)) != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("STUDENT");
            grantList.add(authority);
            password = ((SinhVien) user).getPassWord();
            check = true;
        } else if ((user = giangVienRepositoryImplt.getTeacherInfoByUserName(username)) != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("TEACHER");
            grantList.add(authority);
            password = ((GiangVien) user).getPassWord();
            check = true;
        } else if ((user = nhanVienRepositoryImplt.getStudentByUserName(username)) != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("EMPLOYEE");
            grantList.add(authority);
            password = ((NhanVien) user).getPassWord();
            check = true;
        }
        if (check) {
            UserDetails userDetails = new User(username, passwordEncoder.encode(password), grantList);
            return userDetails;
        } else
            throw new UnsupportedOperationException("Unimplemented method'loadUserByUsername'");
    }

}
