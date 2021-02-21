package com.tingbei.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager; //在OAuth2SecurityConfiguration

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcClientDetailsService jdbcClientDetailsService;

	private static String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from oauth_client_details where client_id = ? and status = 'useful' ";

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//=============存库===========
//		clients.jdbc(dataSource)
//				.withClient("acme")
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//				.scopes("read", "write", "trust")
//				.secret("acme")
//				.accessTokenValiditySeconds(600) //指定任何生成的访问令牌的有效期只有600秒；
//				.refreshTokenValiditySeconds(6000) //指定任何刷新生成令牌的有效期只有6000秒
//				.autoApprove(true);
		jdbcClientDetailsService.setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL); // 修改客户端信息的查询条件
		clients.jdbc(dataSource);//会利用ClientDetailsService类直接检测数据库中oauthClientDetails表内信息
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler) //userApprovalHandler 在OAuth2SecurityConfiguration
				.authenticationManager(authenticationManager);
//		endpoints
//				.tokenStore(tokenStore)
//				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				.tokenKeyAccess("permitAll()")  // 无条件允许访问 (permitAll()  spring security)
				.checkTokenAccess("permitAll()")// 认证的请求用户才能去检测token权限
				.allowFormAuthenticationForClients();
	}

}