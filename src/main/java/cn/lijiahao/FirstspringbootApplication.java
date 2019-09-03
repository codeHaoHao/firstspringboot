package cn.lijiahao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FirstspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstspringbootApplication.class, args);
	}

}
