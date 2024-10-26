package com.jeevan.SpringSecJwt.config;

import com.jeevan.SpringSecJwt.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // it will tell spring that this is a configuration file
@EnableWebSecurity // don't go with the default flow, go with the flow which i will mention here
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService1;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request ->request.
                        requestMatchers("save", "loginuser")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // DaoAuthenticationProvider class implementes the AuthenticationProvider interface indirectlly
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // so the above object need to connect the Database & get the data
        //authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // by this we are not using any password encoder
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authProvider.setUserDetailsService(userDetailsService1);
        return authProvider;
    }

    // for JWT Authentication manager will talk to AuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//
//        // it takes the objetc of
//        Customizer<CsrfConfigurer<HttpSecurity>> CSRFcustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//                customizer.disable();
//            }
//        };
//        http.csrf(CSRFcustomizer);
//        // do this above code in one line
//        //http.csrf(customizer -> customizer.disable());// disabling the csrf
//
//        http.authorizeHttpRequests(request ->request.anyRequest().authenticated()); // every request wil be authrize
//
//        //  http.formLogin(Customizer.withDefaults()); // sign in form will be activated
//
//        Customizer<HttpBasicConfigurer<HttpSecurity>> custBasic = new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(HttpBasicConfigurer<HttpSecurity> basicSecurity) {
//            }
//        };
//        http.httpBasic(custBasic);
//        // do this above code in one line
//        // http.httpBasic(Customizer.withDefaults()); // for postman response
//
////        make http stateless
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



//        http.csrf(customizer -> customizer.disable());// disabling the csrf
//        http.authorizeHttpRequests(request ->request.anyRequest().authenticated()); // every request wil be authrize
//        http.formLogin(Customizer.withDefaults()); // sign in form will be activated
//        http.httpBasic(Customizer.withDefaults()); // for postman response
//
//       // make http stateless
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // use this builder patteren
        // one object is passing to diffrence methods



//    @Bean
//    public UserDetailsService userDetailsService(){
//    // this is how we can customize our user , the below method returning the default
//    UserDetails  user1 = User
//            .withDefaultPasswordEncoder() // do not user this it depricated
//            .username("Shubham")
//            .password("shubhma")
//            .roles("USER")
//            .build();
//
//    UserDetails user2 = User
//            .withDefaultPasswordEncoder()
//            .username("Aditya")
//            .password("aditya")
//            .roles("ADMIN")
//            .build();
//        return new InMemoryUserDetailsManager(user1 , user2);
//    }
}
