package iingyeo.config;

import iingyeo.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by Taemyung on 2015-06-14.
 */
@Configuration
public class OAuth2Config {

    private final static String RESOURCE_ID = "iingyeo";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers("/swagger", "/api-docs/**").anonymous();
            // @formatter:on
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomUserDetailsServiceImpl userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // @formatter:off
            endpoints
                    .tokenStore(this.tokenStore)
                    .tokenServices(tokenServices())
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);

            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
            clients
                    .inMemory()
                    .withClient("iingyeo")
                    .authorizedGrantTypes("password", "refresh_token", "implicit_grant", "authorization_code")
                    .authorities("ROLE_USER")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("1234");
            // @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            tokenServices.setAccessTokenValiditySeconds(3600); // set access token expire time as 1 hour
            return tokenServices;
        }

    }
}
