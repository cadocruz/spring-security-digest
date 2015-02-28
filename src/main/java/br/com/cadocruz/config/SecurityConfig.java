package br.com.cadocruz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers("/api/**").authenticated()
			.and().exceptionHandling()
			// this entry point handles when you request a protected page
			// and
			// you are not yet authenticated
			.authenticationEntryPoint(digestEntryPoint());
		http.csrf().disable();
		http.addFilter(digestAuthenticationFilter(digestEntryPoint()));
	}

	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("cadocruz").password("12345678")
				.authorities("ROLE_USER").and().withUser("admin")
				.password("12345678").authorities("ROLE_USER", "ROLE_ADMIN");
	}
	
	@Bean
	public RestDigestAuthenticationEntryPoint digestEntryPoint() {
		RestDigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new RestDigestAuthenticationEntryPoint();
		digestAuthenticationEntryPoint.setKey("mykey");
		digestAuthenticationEntryPoint.setNonceValiditySeconds(120);
		digestAuthenticationEntryPoint.setRealmName("MyRealm");
		return digestAuthenticationEntryPoint;
	}

	public DigestAuthenticationFilter digestAuthenticationFilter(
			DigestAuthenticationEntryPoint digestAuthenticationEntryPoint)
			throws Exception {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter
				.setAuthenticationEntryPoint(digestEntryPoint());
		digestAuthenticationFilter
				.setUserDetailsService(userDetailsServiceBean());
		return digestAuthenticationFilter;
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

}