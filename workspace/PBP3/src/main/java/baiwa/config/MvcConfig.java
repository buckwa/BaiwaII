package baiwa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/WEB-INF/views/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	}
	
	  // config static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	logger.info("######################");
        registry.addResourceHandler("/theme/**").addResourceLocations("/theme/");
  
        
    }
}
