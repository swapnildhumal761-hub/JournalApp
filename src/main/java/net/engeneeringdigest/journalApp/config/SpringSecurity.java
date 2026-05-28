package net.engeneeringdigest.journalApp.config;

import jakarta.servlet.Filter;
import net.engeneeringdigest.journalApp.filter.JwtFilter;
import net.engeneeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter  jwtFilter;


//①	.authenticationProvider(...)	Registers DaoAuthenticationProvider to verify username/password
//②	.requestMatchers("/public/**").permitAll()	Authorization rule: Anyone can access /public/register — no login needed
//③	.requestMatchers("/journal/**", "/user/**").authenticated()	Authorization rule: /user/** requires a valid authenticated user
//④.requestMatchers("/admin/**").hasRole("ADMIN")	Authorization rule: /admin/** requires the ADMIN role
//⑤.anyRequest().authenticated()	Catch-all: Everything else also requires login
//⑥.httpBasic(...)	Enables the BasicAuthenticationFilter in the filter chain
//⑦.csrf(disable)	Disables CSRF protection (safe for stateless REST APIs with no browser cookies)


//    The order of requestMatchers matters! Spring evaluates them top to bottom and uses the first match.
//    If you put .anyRequest().authenticated() first, /public/** would never be public.


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(daoAuthenticationProvider())   // ① WHO checks credentials
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()     // ② No auth needed
                        .requestMatchers("/journal/**", "/user/**").authenticated()  // ③ Must be logged in
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ④ Must have ADMIN role
                        .anyRequest().authenticated())                 // ⑤ Everything else → login
//                .httpBasic(Customizer.withDefaults())                  // ⑥ Use HTTP Basic Auth  ... here I do httpBasicAuth commit due use of jwtAuthentication
                .csrf(AbstractHttpConfigurer::disable)                 // ⑦ Disable CSRF (for APIs)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


//    DAO = Data Access Object (database)
//    This class knows how to authenticate using DB users
//            It performs:
//    Load user from DB
//    Compare passwords
//    Give roles/authorities

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}

//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeHttpRequests(request -> request
//                        .requestMatchers("/public/**").permitAll()
//                        .requestMatchers("/journal/**", "/user/**").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

