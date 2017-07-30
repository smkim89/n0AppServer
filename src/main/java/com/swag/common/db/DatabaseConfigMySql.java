package com.swag.common.db;

import com.swag.common.util.AES;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfigMySql {
	@Value("#{config['crypto']}")
	protected String CryptoStr;
	
	@Value("#{jdbc['mysql.driver']}")
	private String DB_DRIVER;
	@Value("#{jdbc['mysql.url']}")
	private String DB_URL;
	@Value("#{jdbc['mysql.username']}")
	private String DB_USERNAME;
	@Value("#{jdbc['mysql.password']}")
	private String DB_PASSWORD;

	
	@Bean
	public BasicDataSource dataSourceMySql() throws Exception{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(AES.aesDecodeStr(DB_URL, CryptoStr));
		dataSource.setUsername(AES.aesDecodeStr(DB_USERNAME, CryptoStr));
		dataSource.setPassword(AES.aesDecodeStr(DB_PASSWORD, CryptoStr));
		dataSource.setRemoveAbandoned(true);
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryMySql() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSourceMySql());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/config/sqlmap/**/*MySql.xml"));
		sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/sqlmap/sql-map-config.xml"));
		return sqlSessionFactory;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionMySql() throws Exception{
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryMySql().getObject());
		sqlSession.clearCache();
		return sqlSession;
	}
	
	@Bean
	public DataSourceTransactionManager txManagerMySql() throws Exception{
		return new DataSourceTransactionManager(dataSourceMySql());
	}
}
