package ee.taltech.critter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CritterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CritterApplication.class, args);
    }
}
