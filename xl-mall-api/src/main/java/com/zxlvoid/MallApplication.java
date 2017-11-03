package com.zxlvoid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author whoiszxl
 *
 */
@SpringBootApplication
@MapperScan("com.zxlvoid.dao")
public class MallApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}
	
}
