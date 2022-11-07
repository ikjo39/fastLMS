package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.FastLmsApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	// 2022.10.31

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FastLmsApplication.class);
	}

}
