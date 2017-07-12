package netgloo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by mazi on 31.01.17.
 */
@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/images/**",
            "/css/*/**",
            "/js/*/**",
            "/images/*/**",
            "/",
            "/loadBilder",
            "/*","/index.html","/about.html","/services-2.html","/contact-2.html","/pricing-tables.html",
            "/picture-list.html","/picture-grid.html","/picture-grid","/picture-grid/**","/picture-details/**","/picture-details",
            "/suchen","/shoppingChartSum","/addToChart/**","/shoppingchartSum/*","/login","/login.html","/signup.html","/signup","/register**","/checkout","/VersandDetails",
            "/weiterEinkaufen","/itemDelete/*", "/overview","/overview/*","/BestellungAbsenden","/weitereinkaufen","/bildgruppel/**","/agb.html", "/agb","/faq","/faq.html","/sendMail","/VersandDetailsWithUser",
            "/memberArea","/PDFAbrechnung","/authorize","/memberArea/loeschen","/memberArea/aendern","/forgot-password.html","/forgot-password","/forgot-password/zuruecksetzen","/pay",
            "/createRechnung","/abgeschlossen/**","/abgeschlossen/fertig","/bestellungAbgeschlossen","/load","/download.zip","/load/*","/sendMailcontact","/abgeschlossenOhneDownload","/neueRechnung","/mail","/404.html","/nextSite"
            ,"/email","robots.txt"
    };//"/upload","/upload/*","/upload/*/*","/BackendBilderUpload",

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /*http
                .authorizeRequests()//.anyRequest().permitAll();
                .antMatchers("/resources/**", "/registration","/index","/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();*/
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/404.html");

        http.authorizeRequests().antMatchers("/upload").authenticated();
        http
                .authorizeRequests().
		/*	antMatchers("/**").*/
                antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated().and()
        .formLogin().permitAll().and().logout().permitAll();

/*
        http
                .csrf().disable()
                .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
*/
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("mazi23").password("mazi23").roles("ADMIN");
        //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }
}