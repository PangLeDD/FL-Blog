package com.lei.config;

import com.lei.filter.AdminJwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/1 14:36
 * @Version : 1.0.0
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminJwtAuthenticationTokenFilter JwtAuthenticationTokenFilter;
    @Autowired
    AccessDeniedHandler accessDeniedHandlerImpl;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPointImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求授权  关闭了csrf防护 关闭了session
        http.authorizeRequests()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/upload").permitAll()
//                .antMatchers("/logout").authenticated()
//                .antMatchers("/user/userinfo").authenticated()
//                .antMatchers("upload").authenticated()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关闭自带的登出功能
        http.logout().disable();
        //允许跨域
        http.cors();

        http.addFilterBefore(JwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointImpl)
                .accessDeniedHandler(accessDeniedHandlerImpl);
    }


    //配置加密方式
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}