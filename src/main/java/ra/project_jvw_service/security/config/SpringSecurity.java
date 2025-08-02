package ra.project_jvw_service.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.project_jvw_service.security.jwt.JWTAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurity {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JWTAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Điểm cuối công cộng
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Điểm cuối xác thực
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").hasAnyRole("ADMIN", "MENTOR", "STUDENT")

                        // Điểm cuối của người dùng - Chỉ dành cho QUẢN TRỊ VIÊN
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Điểm cuối của sinh viên
                        .requestMatchers(HttpMethod.GET, "/api/students").hasAnyRole("ADMIN", "MENTOR")
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyRole("ADMIN", "STUDENT")

                        // Điểm cuối của người cố vấn
                        .requestMatchers(HttpMethod.GET, "/api/mentors").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/mentors/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/mentors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/mentors/**").hasAnyRole("ADMIN", "MENTOR")

                        // Các điểm cuối của giai đoạn cố vấn
                        .requestMatchers(HttpMethod.GET, "/api/mentors_phases", "/api/mentorship_phases/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/mentorship_phases").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/mentorship_phases/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/mentorship_phases").hasRole("ADMIN")

                        // Tiêu chí đánh giá điểm cuối
                        .requestMatchers(HttpMethod.GET, "/api/evaluation_criteria", "/api/evaluation_criteria/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/evaluation_criteria").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/evaluation_criteria/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/evaluation_criteria/**").hasRole("ADMIN")

                        // Điểm cuối của vòng đánh giá
                        .requestMatchers(HttpMethod.GET, "/api/assessment_rounds", "/api/assessment_rounds/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/assessment_rounds").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/assessment_rounds/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/assessment_rounds").hasRole("ADMIN")

                        // Điểm cuối tiêu chí vòng
                        .requestMatchers(HttpMethod.GET, "/api/round_criteria", "/api/round_criteria/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/round_criteria").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/round_criteria/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/round_criteria/**").hasRole("ADMIN")

                        // Điểm cuối của nhiệm vụ cố vấn
                        .requestMatchers(HttpMethod.GET, "/api/mentorship_assignments", "/api/mentorship_assignments/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/mentorship_assignments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/mentorship_assignments/**").hasRole("ADMIN")

                        // Điểm cuối của kết quả đánh giá
                        .requestMatchers(HttpMethod.GET, "/api/assessment_results").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/assessment_results").hasRole("MENTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/assessment_results/**").hasRole("MENTOR")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint()))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}