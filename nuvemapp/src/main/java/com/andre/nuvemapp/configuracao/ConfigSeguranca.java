package com.andre.nuvemapp.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigSeguranca {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/oauth/nuvemshop/callback",
                                "/integrations/nuvemshop/install")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) throws Exception{
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        //Mapeia o id da loja para funcionar como login interno, a senha é definida no cadastro
        users.setUsersByUsernameQuery(
                "SELECT loja_id, senha, true FROM loja WHERE loja_id = ?"
        );

        //Seta por padrão o "ROLE_USER" para todas as lojas
        users.setAuthoritiesByUsernameQuery(
                "SELECT loja_id, 'ROLE_USER' FROM loja WHERE loja_id = ?"
        );

        return users;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
            return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

