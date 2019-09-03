package cn.lijiahao.interceptor;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class SpringMVCConfig extends WebMvcConfigurationSupport{
	@Autowired
	private BaseInterceptor baseInterceptor;
	
	private List<String> excluPathPatterns;//不被拦截的请求
	
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		initExcludePathPatterns();
		registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns(excluPathPatterns);
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/css/favicon.ico");
	}
	/**
	 * 初始化不被拦截的请求
	 */
	private void initExcludePathPatterns() {
		try {
			excluPathPatterns = FileUtils.readLines(ResourceUtils.getFile("classpath:springmvc-interceptor.properties"), "utf-8");
			initList();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void initList() {
		for(int i=0;i<excluPathPatterns.size();i++) {
			if(excluPathPatterns.get(i).startsWith("###")) {
				excluPathPatterns.remove(i);
			}
			if(excluPathPatterns.get(i).equals("excluPathPatterns:")) {
				excluPathPatterns.remove(i);
			}
		}
	}
}
