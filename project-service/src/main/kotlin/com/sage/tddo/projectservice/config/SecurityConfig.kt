package com.sage.tddo.projectservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.IpAddressMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf(){csrf -> csrf.disable()}
            .cors(){cors -> cors.disable()}
            .authorizeHttpRequests() { authorize ->
                authorize.requestMatchers("/**").permitAll()
                authorize.requestMatchers(IpAddressMatcher("127.0.0.1")).permitAll()

            }
            .sessionManagement() { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }
}
