package org.numahop.numahop;

import org.numahop.numahop.security.AjaxAuthenticationFailureHandler;
import org.numahop.numahop.security.AjaxAuthenticationSuccessHandler;
import org.numahop.numahop.security.AjaxLogoutSuccessHandler;
import org.numahop.numahop.security.Http401UnauthorizedEntryPoint;
import org.numahop.numahop.service.LockService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;

@Configuration
public class ApplicationSecurityTest {

	@Bean
	public UserDetailsService userDetailsService() {
		return Mockito.mock(UserDetailsService.class);
	}

	@Mock
	public LockService lockService;

	@Bean
	public RememberMeServices rememberMeServices() {
		return new NullRememberMeServices();
	}

	@Bean
	public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
		return new AjaxAuthenticationSuccessHandler();
	}

	@Bean
	public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
		return new AjaxAuthenticationFailureHandler();
	}

	@Bean
	public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
		return new AjaxLogoutSuccessHandler(lockService);
	}

	@Bean
	public Http401UnauthorizedEntryPoint authenticationEntryPoint() {
		return new Http401UnauthorizedEntryPoint();
	}

}
