package com.kutaybezci.apartment.frontend;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String USER_BY_USER_NAME_SQL = "select u.username,u.password, u.enabled from user u  where u.username=?";
	private static final String ROLES_BY_USER_NAME_SQL = //
			/*
			 * "SELECT \n" + // "    user.username, permission.permission_name\n" + //
			 * "FROM\n" + // "    user,\n" + // "    role,\n" + // "    user_role,\n" + //
			 * "    role_permission,\n" + // "    permission\n" + // "WHERE\n" + //
			 * "    user.username = ?\n" + //
			 * "        AND user.user_id = user_role.user_id\n" + //
			 * "        AND user_role.role_id = role.role_id\n" + //
			 * "        AND user_role.role_id = role_permission.role_id\n" + //
			 * "        AND role_permission.role_permission_id = permission.permission_id";
			 */
			"select u.username, r.role_name from user u, role r, user_role us where us.user_id=u.user_id and us.role_id=r.role_id and u.username=?";
	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USER_BY_USER_NAME_SQL)
				.authoritiesByUsernameQuery(ROLES_BY_USER_NAME_SQL);

	}

	// For now development purposes
	@SuppressWarnings("deprecation")
	@Bean
	public NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/img/**", "/", "/home", "/webjars/**", "/login", "/login-error")
				.permitAll()//
				.anyRequest().authenticated()//
				.and().formLogin().loginPage("/login").failureUrl("/login-error").usernameParameter("username")
				.passwordParameter("password").loginProcessingUrl("/balance")//
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/home")//
				.and().exceptionHandling().accessDeniedPage("/403")//
				.and().csrf();

	}

}
