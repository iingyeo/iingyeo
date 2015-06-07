package iingyeo.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Taemyung on 2015-06-07.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                //Root level documentation
                .apiInfo(new ApiInfo(
                        "Iingyeo SNS Rest API",
                        "This service provides Rest API of the anonymous iingyeo SNS",
                        null,
                        null,
                        null,
                        null
                ))
                .useDefaultResponseMessages(false)
                        //Map the specific URL patterns into Swagger
                .includePatterns("/users", "/users/.*");
    }

}
