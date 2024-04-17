package com.laptrinhweb.thitracnghiem;

import org.springframework.context.ApplicationContext;

import com.laptrinhweb.thitracnghiem.Entity.CTBaiThi;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CTBaiThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ThitracnghiemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ThitracnghiemApplication.class, args);
		CTBaiThiRepository repository = context.getBean(CTBaiThiRepository.class);
	}

}
