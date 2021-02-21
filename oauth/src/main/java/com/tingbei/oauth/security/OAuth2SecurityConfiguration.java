package com.tingbei.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;


	/**
	 * spring_security 身份验证的默认登陆用户名和密码
	 * @param auth 入参
	 * @throws Exception 抛出异常
	 */
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//        .withUser("bill").password("abc123").roles("ADMIN").and()
//        .withUser("bob").password("abc123").roles("USER");
//		auth.jdbcAuthentication()
//				.dataSource(dataSource)
//				.usersByUsernameQuery("select user_name as principal, password as credentials, enabled from oauth_users where user_name = ?")
//				.authoritiesByUsernameQuery("select user_name as principal, authority as role from oauth_users where user_name = ?")
//				.rolePrefix("ROLE_");
		// 2017-12-29 将security的授权改到user_info 表
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select ui_loginname as principal, ui_password as credentials, security_enabled from user_info where ui_loginname = ?")
				.authoritiesByUsernameQuery("select ui_loginname as principal, security_authority as role from user_info where ui_loginname = ?")
				.rolePrefix("ROLE_");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin().loginPage("/login").permitAll()
				.and()
				.requestMatchers().antMatchers("/login","/oauth/authorize", "/oauth/confirm_access","/login/github","/oauth/error")
				.and()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.csrf().disable();
	}

    //将web登录和oauth登录的manager共享
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	@Bean
	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService()));
		handler.setClientDetailsService(clientDetailsService());
		return handler;
	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

	/**
	 * 从数据库获取客户端详细信息
	 * @return 返回一个ClientDetailsService
	 */
	@Bean
	public ClientDetailsService clientDetailsService(){
		return new JdbcClientDetailsService(dataSource);
	}

}
