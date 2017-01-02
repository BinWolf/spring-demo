package com.wolf.springmvc.web;

import com.wolf.springmvc.response.spring.CommonResponseFastJsonConverter;
import com.wolf.springmvc.response.spring.HandlerBusinessExceptionResolver;
import com.wolf.springmvc.response.spring.IResponseMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 16/12/30.
 *
 * web 相关的一下配置，页面控制器，拦截器，参数注入等
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.wolf.springmvc")
public class WebMvcConfigurationAdapter extends WebMvcConfigurerAdapter {

    /**
     * 页面映射处理
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /**
     * 处理静态资源请求
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor());
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * 页面跳转设置
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/toUpload").setViewName("upload");
    }

    /**
     * spring mvc 默认会去掉"."后面的值会被去掉，以下设置会保存"."后面的值
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    /**
     * 文件上传需要用到
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1000);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    /**
     * 返回值处理
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(processor());
    }
    @Bean
    public IResponseMethodProcessor processor(){
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(converter());
        return new IResponseMethodProcessor(converters);
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
    }
    @Bean
    public CommonResponseFastJsonConverter converter(){
        return new CommonResponseFastJsonConverter();
    }

    /**
     * 自定义异常处理
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(exceptionResolver());
    }
    @Bean
    public HandlerBusinessExceptionResolver exceptionResolver(){
        return new HandlerBusinessExceptionResolver();
    }
}
