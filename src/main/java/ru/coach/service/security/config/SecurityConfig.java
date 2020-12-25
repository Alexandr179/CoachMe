package ru.coach.service.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.coach.service.security.filter.TokenAuthenticationFilter;
import ru.coach.service.security.providers.TokenAuthenticationProvider;


import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired// ..уже есть UserDetailsServiceImpl implements UserDetailsService (и есть по умолчанию inMemoryUserDetailsManager)
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;// какой инжектим?.. кастомный или реализацию default-ную. поставим-ка ..@Qualifier)

    @Autowired
    @Qualifier("bcPasswordEncoder")// см. @Bean in Application
    private PasswordEncoder passwordEncoder;// (нужно сказать spring-sec. каким passwordEncoder мы пользуемся)

    @Autowired
    @Qualifier(value = "dataSource")// это для создания таблицы token-нов для JSESSION по "remember-me"
    private DataSource dataSource;


    // REST ..
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;



    @Autowired
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * комментим и на page's вводим csrf token-ны. все) в Spring Security заложено по умолчанию csrf
         **/
        /**
         * можем полностью отключить csrf (csrf().disable()) - на REST
         **/
//        http.csrf().disable();
        // TODO: REST auth.. оставляем только то, что разрешаем браузеру
//        http.sessionManagement().disable();// отключаем на REST сессии..
//        http.formLogin().disable();
//        http.logout().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ..иначе, если сессии отключить - 2H DB не законнектится
        http.addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);// rest.. !!используем фильтр
//        http.headers().frameOptions().disable();// + для работы DB !если только мы отключили сессии..

        // TODO: web auth..
        http.    authorizeRequests()
                .antMatchers("/signUp").permitAll()// доступно всем
                .antMatchers("/users").authenticated()// тем, кто прошел форму логина (аутентифицирован)
//                                                 .hasAuthority("ADMIN");
                .and()
                .formLogin()
                .loginPage("/signIn")// страница ВХОДА
                .usernameParameter("email")// авторизация ..все-таки) идет по полю 'email'
                .defaultSuccessUrl("/users")// успешная аутентификация

//                .failureUrl("/signIn?error")// ..не пройдена >> на signIn с парам.error по default-ту.

                /** нужно указать где (в DB) будут хранится rememberMe-token и данные сессии
                    (реализация см. persistentTokenRepository)
                 не забываем для не обновления таблицы указывать property: spring.datasource.initialization-mode=always**/
                .and()
//                .rememberMe().alwaysRemember(true)
                .rememberMe().rememberMeParameter("remember-me")// система запоминает сессию user-ра
                .tokenRepository(persistentTokenRepository())// создаем для этого token-репозиторий (см.ниже в security config ..@been)
                // работает с таблицей DB persistent_logins
                .tokenValiditySeconds(365 * 24 * 60 * 60)// столько будет валиден token (будет храниться сессия)

                .and()
                .logout()
//                .logoutUrl("/logout") ..нововведения. см.ниже:
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/signIn")
                .deleteCookies("remember-me", "JSESSIONID")// "remember-me", ..когда используем "remember-me",
                .invalidateHttpSession(true)// ..и завершает сессию user-ра
        ;
    }


    /** пользуемся нашим Service (..userDetailsService)
    // закладываем в Spring Security userDetailsService: нашу реализацию сервиса.. завершая конфиг., добавляем в реализацию password
    **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //REST auth.. используем провайдер
        if (TokenAuthenticationProvider.isRestTokenAuth) {
            auth.authenticationProvider(tokenAuthenticationProvider);
        } else {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);// web auth..
        }
    }


    /** хранение rememberMe-token и данных сессии User-ра **/
    @Bean// запоминает JSESSION того user-а, кто нажал галку)
    public PersistentTokenRepository persistentTokenRepository(){// сохраняет token's.. для "remember-me" параметра
         JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();// создает таблицу.. persistent_logins
                                                            //  (тк. в app.prop  ..spring.datasource.initialization-mode=always)
                                                           // когда в app.properties есть: spring.datasource.initialization-mode=always
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
