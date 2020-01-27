package cn.lijiahao.config;

import cn.lijiahao.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringMVCConfig extends WebMvcConfigurationSupport{
	@Autowired
	private BaseInterceptor baseInterceptor;
	
	private List<String> excluPathPatterns = new ArrayList<>();//不被拦截的请求
	
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		initExcludePathPatterns();
		registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns(excluPathPatterns);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations ("classpath:/static/css/favicon.ico");
	}
	/**
	 * 初始化不被拦截的请求
	 */
	private void initExcludePathPatterns() {
		ClassPathResource classPathResource = new ClassPathResource("springmvc-interceptor.properties");
		try(BufferedReader bfreader =  new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {

			String tempContent;
			while ((tempContent = bfreader.readLine())!= null){
				if (tempContent.startsWith("###")){
					continue;
				} else if(tempContent.equals("excluPathPatterns:")){
					continue;
				}
				excluPathPatterns.add(tempContent);
			}
//			excluPathPatterns = FileUtils.readLines(classPathResource.getFile(), "utf-8");
//			initList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
