package baiwa.config;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
@ComponentScan("baiwa,com.bukwa")
@EnableTransactionManagement
@PropertySource(value = { "classpath:/application.properties" })
public class ApplicationConfig {
	
	
	@Autowired
    private Environment env;
 
	
	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		return dataSource;
	}
	
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
    public DataSourceTransactionManager transactionManager() {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }
	@Bean
	public HttpComponentsMessageSender httpComponentsMessageSender() {
	    HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	    messageSender.setConnectionTimeout(30000);
	    return messageSender;
	}
	
	@Bean
	public WebServiceTemplate webServiceTemplate() {
	    WebServiceTemplate template = new WebServiceTemplate();
	    template.setMessageSender(httpComponentsMessageSender());
	    return template;
	}
	
	
    @Bean
    public MessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(  "i18/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
}
