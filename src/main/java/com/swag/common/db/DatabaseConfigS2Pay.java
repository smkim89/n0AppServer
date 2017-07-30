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
public class DatabaseConfigS2Pay {
	@Value("#{config['crypto']}")
	protected String CryptoStr;

	@Value("#{jdbc['s2pay.driver']}")
	private String DB_DRIVER;
	@Value("#{jdbc['s2pay.url']}")
	private String DB_URL;
	@Value("#{jdbc['s2pay.username']}")
	private String DB_USERNAME;
	@Value("#{jdbc['s2pay.password']}")
	private String DB_PASSWORD;


	@Bean
	public BasicDataSource dataSourceS2Pay() throws Exception{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(AES.aesDecodeStr(DB_URL, CryptoStr));
		dataSource.setUsername(AES.aesDecodeStr(DB_USERNAME, CryptoStr));
		dataSource.setPassword(AES.aesDecodeStr(DB_PASSWORD, CryptoStr));
		dataSource.setRemoveAbandoned(true);
		
		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryS2Pay() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSourceS2Pay());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/config/sqlmap/**/*S2Pay.xml"));
		sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/sqlmap/sql-map-config.xml"));
		return sqlSessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionS2Pay() throws Exception{
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryS2Pay().getObject());
		sqlSession.clearCache();
		return sqlSession;
	}

	@Bean
	public DataSourceTransactionManager txManagerS2Pay() throws Exception{
		return new DataSourceTransactionManager(dataSourceS2Pay());
	}
}
