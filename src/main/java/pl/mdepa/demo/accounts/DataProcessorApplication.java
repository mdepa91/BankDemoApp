package pl.mdepa.demo.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ConfigurationPropertiesScan
public class DataProcessorApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataProcessorApplication.class, args);
  }
}
