package baiwa.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import baiwa.util.BaiwaConstants;
import baiwa.util.CustomLogoutHandler;
 
@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
 

  	@Autowired
	@Qualifier("authenticationProvider")
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Nomal");
		auth.authenticationProvider(authenticationProvider);
	}
 
  /**
   * 1. Define a DaoAuthenticationProvider bean. This bean use our own
   * UserDetailsService implementation for load user from database
   */
//  @Bean
//  DaoAuthenticationProvider daoAuthenticationProvider(MyUserDetailsService myUserDetailsService) {
//    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//    daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
//    return daoAuthenticationProvider;
//  }
 
  /**
   * 2. Use above bean for Authentication
   */
//  @Autowired
//  public void configAuthenticationProvider(AuthenticationManagerBuilder auth,
//      DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
//    auth.authenticationProvider(daoAuthenticationProvider);
//  }
 
  /**
   * 3. Define access rules based on request url pattern. 
   * The rulese in this demo: 
   *    Anyone can access homepage / 
   *    Anyone can access static resources like /imgs/** 
   *    Only "ADMIN" role can access url under /admin/** 
   *    Only "USER" role can access url under /**
   */
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().antMatchers("/").permitAll(); 
//    http.authorizeRequests().antMatchers("/imgs/**").permitAll(); 
//    http.authorizeRequests().antMatchers("/theme/**").permitAll(); 
//    http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
//    http.authorizeRequests().antMatchers("/**").hasRole("USER").and().formLogin();
//  }
//  
  
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.anyRequest().fullyAuthenticated()
//				.and()
//			.formLogin();
//	}

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		System.out.println("Ldap");
//		auth.authenticationProvider(authenticationProvider)
//			.ldapAuthentication()
//			.userSearchFilter("(uid={0})")
//				.contextSource(contextSource());
//	}
//
//	@Bean
//	public DefaultSpringSecurityContextSource contextSource() {
//		return  new DefaultSpringSecurityContextSource(Arrays.asList("ldap://161.246.34.181:389"), "dc=kmitl,dc=ac,dc=th");
//	}
	
  
  @Override
 	protected void configure(HttpSecurity http) throws Exception {
 		http.authorizeRequests()
 			.antMatchers(
 				"/",
 				"/resources/**",
 				"/theme/**",
 				"/img/**",
 				"/baiwa/**",
 				"/json/**",
 				"/rest/**",
 				"/WEB-INF/jsp/**",
 				"/index.jsp"
 			).permitAll()
 			//.antMatchers("/admin/**").hasRole("ADMIN")
 			.anyRequest().hasAuthority(BaiwaConstants.ROLE_USER)
 		.and()
 		.formLogin()
 			.loginPage(BaiwaConstants.LOGIN_URL).permitAll()
 			.defaultSuccessUrl(BaiwaConstants.LOGIN_SUCCESS_URL)
 			//.successHandler(duplicateLoginAuthenticationSuccessHandler(BaiwaConstants.LOGIN_SUCCESS_URL, BaiwaConstants.LOGIN_WARNING_URL))
 			.failureUrl(BaiwaConstants.LOGIN_ERROR_URL).permitAll()
 			.usernameParameter("username")
 			.passwordParameter("password")
 		.and()
 		.logout()
 			.addLogoutHandler(customLogoutHandler())
 			.logoutUrl(BaiwaConstants.LOGOUT_URL)
 			.logoutSuccessUrl(BaiwaConstants.LOGIN_URL)
 			.invalidateHttpSession(true)
 			.clearAuthentication(true)
 		.and()
 		.exceptionHandling()
 			.accessDeniedPage("/403.htm")
 		.and()
 		.sessionManagement()
 			.maximumSessions(5)
 			//.sessionRegistry(sessionRegistry())
 			.expiredUrl(BaiwaConstants.LOGIN_URL);
 		
 		http.csrf().disable();
 	}
   
   
 	@Bean(name = "customLogoutHandler")
 	public CustomLogoutHandler customLogoutHandler() {
 		return new CustomLogoutHandler();
 	}
 	
 	@Bean(name = "passwordEncoder")
	public Md5PasswordEncoder passwordEncoder() {
 		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder;
	}
}