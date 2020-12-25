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
    private PasswordEncoder passwordEncoder;//нужно сказать spring-sec. каким passwordEncoder мы пользуемся

    @Autowired
    @Qualifier(value = "dataSource")// это для создания таблицы token-нов для JSESSION по "remember-me"
    private DataSource dataSource;



    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;



    @Autowired
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();//  описываем ниже  csrf() закомментили в SpringBoot

        // TODO: REST auth..
        //http.sessionManagement().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ..иначе, если сессии отключить - 2H DB не законнектится
//        http.addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);// !не забываем сказать об использовании фильтра
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll();
//                .antMatchers("/users/**").authenticated();// доступно только аутентифицированным (*только ресурс пройдет)
//                .antMatchers("/disciplines/**").hasAuthority("ADMIN");// проходит только ADMIN (тест... token1,2)
//        http.headers().frameOptions().disable();// + для работы DB

        // TODO: web auth..
        http.    authorizeRequests()
                .antMatchers("/signUp").permitAll()// доступно всем
                .antMatchers("/users").authenticated()// тем, кто прошел форму логина (аутентифицирован)

                .and()
                .formLogin()
                .loginPage("/signIn")// страница ВХОДА
                .usernameParameter("email")// авторизация ..все-таки) идет по полю 'email'
                .defaultSuccessUrl("/users")// успешная аутентификация
                .failureUrl("/signIn?error")// ..не пройдена >> на signIn с парам.error по default-ту.

                // не мешает работе SpringBoot реализации....
                //https://stackoverflow.com/questions/50199266/how-to-handle-session-creation-and-adding-hidden-input-csrf-token-for-any-page-c
                .and()// TODO: >> фильтр подхватывает token's из html hidden-полей
                .csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository())

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


    @Override// пользуемся Нашим Service -> ..userDetailsService
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // web auth..
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        // REST auth..
//        auth.authenticationProvider(tokenAuthenticationProvider);
    }


    // web auth..
    @Bean// запоминает JSESSION того user-а, кто нажал галку)
    public PersistentTokenRepository persistentTokenRepository(){// сохраняет token's.. для "remember-me" параметра
         JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();// создает таблицу.. persistent_logins
                                                            //  (тк. в app.prop  ..spring.datasource.initialization-mode=always)
                                                           // когда в app.properties есть: spring.datasource.initialization-mode=always
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
