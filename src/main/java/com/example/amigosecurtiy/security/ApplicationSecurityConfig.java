package com.example.amigosecurtiy.security;

import com.example.amigosecurtiy.security.roles.UserPermission;
import com.example.amigosecurtiy.security.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig( PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.csrf().disable() //TODO: i will learn on  next section
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(UserRole.STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //.antMatchers("/management/api/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.ADMINTRAINEE.name())
        //.antMatchers("/api/admin/**").hasRole(UserRole.ADMIN.name())
        //.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
        //.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
        //.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
        //.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.ADMINTRAINEE.name())
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
       UserDetails user = User.builder()
               .username("James Bond")
               .password(passwordEncoder.encode("password"))
              // .roles(UserRole.STUDENT.name())
               .authorities(UserRole.STUDENT.getGrantedAuthority())
               .build();
       UserDetails adminLinda = User.builder()
               .username("linda")
               .password(passwordEncoder.encode("123"))
               //.roles(UserRole.ADMIN.name())
               .authorities(UserRole.ADMIN.getGrantedAuthority())
               .build();
       UserDetails tomTrainee = User.builder()
               .username("Tom")
               .password(passwordEncoder.encode("123"))
              // .roles(UserRole.ADMINTRAINEE.name())
               .authorities(UserRole.ADMINTRAINEE.getGrantedAuthority())
               .build();

       return new InMemoryUserDetailsManager(
               user,adminLinda,tomTrainee
       );
    }
}
