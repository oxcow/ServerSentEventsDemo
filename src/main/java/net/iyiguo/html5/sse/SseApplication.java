package net.iyiguo.html5.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.iyiguo.html5.sse.service.TestBean;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class SseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SseApplication.class, args);
    }

    /**
     * 被@Bean注解的类，当关闭容器时会自动调用其close/shutdown方法。
     *
     * @return
     */
    @Bean
    public TestBean testBean() {
        return new TestBean();
    }
}
