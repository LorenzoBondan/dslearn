package com.devsuperior.dslearnbds.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.dslearnbds.components.JwtTokenEnhancer;

//############ AUTHORIZATION SERVER

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	private String clientId; // VARIÁVEL DA APPLICATION.PROPERTIES
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret; // VARIÁVEL DA APPLICATION.PROPERTIES
	
	@Value("${jwt.duration}")
	private Integer jwtDuration; // VARIÁVEL DA APPLICATION.PROPERTIES
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	// INJETAR O BEAN QUE FIZEMOS NA CLASSE APPCONFIG
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// INJETAR AS INFORMAÇÕES A MAIS NO TOKEN DO COMPONENT JWTTOKENENHANCER
	@Autowired
	private JwtTokenEnhancer tokenEnhancer;
	
	// INJETAR PARA O REFRESH TOKEN
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient(clientId) // NOME QUE O BACKEND RECEBE
		.secret(passwordEncoder.encode(clientSecret)) // SENHA DA APLICAÇÃO CRIPTOGRAFADA
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(jwtDuration) // TEMPO DE VALIDAÇÃO DO TOKEN
		.refreshTokenValiditySeconds(jwtDuration); // ACRESCENTADO DEPOIS, REFRESH TOKEN
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		// INFORMAÇÕES A MAIS NO TOKEN
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));
		
		// QUEM QUE VAI AUTORIZAR E QUAL VAI SER O FORMATO DO TOKEN
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore) // OBJETO RESPONSÁVEL POR GERIR O TOKEN
		.accessTokenConverter(accessTokenConverter)
		
		.tokenEnhancer(chain)
		
		.userDetailsService(userDetailsService); // PARA O REFRESH TOKEN
	}

	
}
