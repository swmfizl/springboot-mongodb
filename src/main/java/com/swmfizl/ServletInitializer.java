package com.swmfizl;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * springboot-mongodb
 * 
 * @author 钟力
 * @version 1.0.0
 * @WeChat swmfizl
 * @Time 2020-03-09
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootMongodbApplication.class);
	}

}
