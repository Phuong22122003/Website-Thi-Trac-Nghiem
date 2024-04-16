package com.laptrinhweb.thitracnghiem;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.SinhVienRepository;
import com.laptrinhweb.thitracnghiem.Service.QuanLiGiangVienService;
import com.laptrinhweb.thitracnghiem.Service.QuanLyLopHocService;

@SpringBootApplication
public class ThitracnghiemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ThitracnghiemApplication.class, args);
		System.out.println("hello");

		SinhVienRepository sVienRepository = context.getBean(SinhVienRepository.class);
		SinhVien sv = sVienRepository.findSinhVienByMasv("N21DCCN067     ");
		System.out.println(sv.get_this().toString());
	}

}
