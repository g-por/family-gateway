@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex

                        .pathMatchers(HttpMethod.POST, "/api/users/auth/register").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/users/auth/login").permitAll()
                        .pathMatchers("/actuator/**").permitAll()


                        .anyExchange().authenticated()
                )
                .build();
    }
}
