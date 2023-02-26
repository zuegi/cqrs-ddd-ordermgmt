package ch.zuegi.ordermgmt.config;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodConfig {


    @Bean
    ScanResult scanResult() {
        return new ClassGraph()
                .disableJarScanning()
                .enableAllInfo()
                .scan();
    }
}
