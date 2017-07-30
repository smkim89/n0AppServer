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
public class DatabaseConfigAppTo {
	@Value("#{config['crypto']}")
	protected String CryptoStr;

	@Value("#{jdbc['appto.driver']}")
	private String DB_DRIVER;
	@Value("#{jdbc['appto.url']}")
	private String DB_URL;
	@Value("#{jdbc['appto.username']}")
	private String DB_USERNAME;
	@Value("#{jdbc['appto.password']}")
	private String DB_PASSWORD;


	@Bean
	public BasicDataSource dataSourceAppTo() throws Exception{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(AES.aesDecodeStr(DB_URL, CryptoStr));
		dataSource.setUsername(AES.aesDecodeStr(DB_USERNAME, CryptoStr));
		dataSource.setPassword(AES.aesDecodeStr(DB_PASSWORD, CryptoStr));
		dataSource.setRemoveAbandoned(true);
		
		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryAppTo() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSourceAppTo());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/config/sqlmap/**/*AppTo.xml"));
		sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/sqlmap/sql-map-config.xml"));
		return sqlSessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionAppTo() throws Exception{
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryAppTo().getObject());
		sqlSession.clearCache();
		return sqlSession;
	}

	@Bean
	public DataSourceTransactionManager txManagerAppTo() throws Exception{
		return new DataSourceTransactionManager(dataSourceAppTo());
	}
}
