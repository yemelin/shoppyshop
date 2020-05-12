package com.example.shoppyshop;
import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
public class ShoppyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppyshopApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	@Bean
	public NullAwareBeanUtilsBean nullAwareBeanUtilsBean() {
		return new NullAwareBeanUtilsBean();
	}
}
