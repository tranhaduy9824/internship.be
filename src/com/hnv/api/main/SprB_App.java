package com.hnv.api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hnv.def.DefAPIExt;

@SpringBootApplication(scanBasePackages={DefAPIExt.API_PATH_MAIN, DefAPIExt.API_PATH_SV_MAIN})
public class SprB_App {
	public static void main(String[] args) {
		System.setProperty("spring.http.multipart.max-file-size"		, "100MB");
		System.setProperty("spring.http.multipart.max-request-size"		, "100MB");
		System.setProperty("spring.servlet.multipart.max-file-size"		, "100MB");
		System.setProperty("spring.servlet.multipart.max-request-size"	, "100MB");
		SpringApplication.run(SprB_App.class, args);
	}
}