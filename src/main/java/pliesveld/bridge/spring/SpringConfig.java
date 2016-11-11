package pliesveld.bridge.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pliesveld.bridge.WicketApplication;


@Configuration
@ComponentScan(basePackages = "pliesveld.bridge")
@PropertySource(value = {"classpath:database.properties"})
public class SpringConfig {
    @Bean
    public WicketApplication wicketApplication() {
        return new WicketApplication();
    }

}
