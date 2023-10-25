package com.betrybe.agrix;

import com.betrybe.agrix.util.SecurityConfiguration;
import com.betrybe.agrix.util.SecurityFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Application main class 2.
 */
@SpringBootApplication
@Import({SecurityConfiguration.class, SecurityFilter.class})
public class AgrixApplication {

  public static void main(String[] args) {
    SpringApplication.run(AgrixApplication.class, args);
  }

}
