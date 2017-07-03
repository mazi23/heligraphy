package netgloo.config;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.MultipartConfigElement;

/**
 * Created by mazi on 30.01.17.
 */
@Configuration
public class SpringMVCConfiguration extends WebMvcAutoConfiguration {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5120MB");
        factory.setMaxRequestSize("5120MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
              /*  container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/404.html"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/404.html"));*/
            }
        };
    }
}
