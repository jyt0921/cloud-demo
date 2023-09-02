package cn.itcast.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    /*设置Feign日志级别*/
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
