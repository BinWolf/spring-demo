package com.wolf.book.ch2.el;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

/**
 * Created by wolf on 16/12/23.
 */

@Configuration
@ComponentScan("com.wolf.book.ch2.el")
@PropertySource("classpath:test.properties")
public class ElConfig {
    @Value("L Love Wolf")
    private String normal;

    @Value("#{ systemProperties['os.name'] }")
    private String osName;

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNum;

    @Value("#{domeService.another}")
    private String fromAnother;

    @Value("classpath:test.txt")
    private Resource testFile;

    @Value("http://www.baidu.com")
    private Resource url;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Environment environment; // 资源文件可以在该变量中读取

    /**
     * 如果读取资源文件用@Value需要注入该bean
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource() {
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNum);
            System.out.println(fromAnother);
            System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(url.getInputStream()));
            System.out.println(environment.getProperty("book.author"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
