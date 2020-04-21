package com.example.decryptor.config;

import com.example.decryptor.advice.CommonAdvice;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@Data
@ConditionalOnClass(CommonAdvice.class)
public class CommonConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public CommonAdvice ExecuteTimeTrackerAdvice(){
        return new CommonAdvice();
    }
}

