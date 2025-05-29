package com.hnv.api.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hnv.def.DefAPIExt;

@Configuration
public class SprB_CfgMultipartRequest {
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);
		resolver.setMaxInMemorySize	(DefAPIExt.API_UPLOAD_MAX_MEM);
		resolver.setMaxUploadSize	(DefAPIExt.API_UPLOAD_MAX_SIZE);
		return resolver;
	}
}



