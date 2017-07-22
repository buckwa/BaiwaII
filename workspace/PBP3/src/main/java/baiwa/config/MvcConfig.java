package baiwa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;


@EnableWebMvc
@ComponentScan("baiwa.controller,com.buckwa")
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		logger.info("Configure View Resolvers");		
		registry.tiles();
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		logger.info("Tiles Configurer");		
		TilesConfigurer tilesconfigurer = new TilesConfigurer();		 
		tilesconfigurer.setDefinitions(new String[] {
			"/WEB-INF/defs/baiwa/common.xml"
		});
		tilesconfigurer.setCheckRefresh(true);		
		return tilesconfigurer;
	}
	
	  // config static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	logger.info("######################");
//        registry.addResourceHandler("/theme/**").addResourceLocations("/theme/");
//        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
//        registry.addResourceHandler("/baiwa/**").addResourceLocations("/baiwa/");
//        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
//        registry.addResourceHandler("/baiwa/*.js").addResourceLocations("/baiwa/");
//        registry.addResourceHandler("/baiwa/*.css").addResourceLocations("/baiwa/");
        
        registry.addResourceHandler("/theme/**").addResourceLocations("/theme/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/baiwa/app/**").addResourceLocations("/baiwa/app/");
        registry.addResourceHandler("/baiwa/libs/**").addResourceLocations("/baiwa/libs/");
        registry.addResourceHandler("/baiwa/node_modules/**").addResourceLocations("/baiwa/node_modules/");
        registry.addResourceHandler("/baiwa/*.js").addResourceLocations("/baiwa/");
        registry.addResourceHandler("/baiwa/*.css").addResourceLocations("/baiwa/");
        registry.addResourceHandler("/baiwa/dist/**").addResourceLocations("/baiwa/dist/");
        
    }
    
    
    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000000);
        return multipartResolver;
    }
}
