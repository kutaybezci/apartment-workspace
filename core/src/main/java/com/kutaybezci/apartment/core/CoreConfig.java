package com.kutaybezci.apartment.core;

import javax.sql.DataSource;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.kutaybezci.apartment.core.bl.converter.PersonDtoToEntity;

@Configuration
public class CoreConfig {
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}
	
    @Bean
    public ConversionService conversionService () {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new PersonDtoToEntity());
        return service;
    }
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
         
        MethodValidationPostProcessor processor =
                new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }
     
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }  
}
