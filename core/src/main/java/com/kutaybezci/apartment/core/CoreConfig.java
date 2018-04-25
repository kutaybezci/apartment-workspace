package com.kutaybezci.apartment.core;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kutaybezci.apartment.core.bl.converter.PersonDtoToEntity;
import com.kutaybezci.apartment.core.bl.converter.PersonEntityToDto;
import com.kutaybezci.apartment.core.bl.converter.UserEntityToDto;

@Configuration
public class CoreConfig {
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	@Bean
	public ConversionService conversionService() {
		DefaultConversionService service = new DefaultConversionService();
		service.addConverter(new PersonDtoToEntity());
		service.addConverter(new PersonEntityToDto());
		service.addConverter(new UserEntityToDto());
		return service;
	}

}
