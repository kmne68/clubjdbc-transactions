package com.kemery;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ClubDBConfiguration {

	@Bean
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/club296?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("@tbftgoGg1sbmLam!0i");
		
		return dataSource;
	}
	
	
	@Bean
	public MemberService memberService() {
		
		MemberServiceJdbcTxImplWithSpring bean = new MemberServiceJdbcTxImplWithSpring();
		
		bean.setDataSource(dataSource());
		
		return bean;
		
	}
	
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
}

