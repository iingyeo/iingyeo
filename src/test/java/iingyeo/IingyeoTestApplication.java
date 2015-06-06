package iingyeo;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class IingyeoTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IingyeoTestApplication.class, args);
    }

    @Bean(destroyMethod = "close")
    public Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder().build();
    }

}
