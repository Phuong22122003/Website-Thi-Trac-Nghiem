package com.laptrinhweb.thitracnghiem;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.laptrinhweb.thitracnghiem.Repository.CauHoiRepository;
@SpringBootApplication
public class ThitracnghiemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ThitracnghiemApplication.class, args);
		System.out.println("hello");
		CauHoiRepository cauHoiRepository = context.getBean(CauHoiRepository.class);
		cauHoiRepository.findAll().forEach(e->System.out.println(e));
	}

}
