package baiwa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;


@EnableWebMvc
@ComponentScan("baiwa.controller")
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
        registry.addResourceHandler("/theme/**").addResourceLocations("/theme/");
  
        
    }
}
