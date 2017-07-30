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
public class DatabaseConfigPayco {
	@Value("#{config['crypto']}")
	protected String CryptoStr;

	@Value("#{config['MODE']}")
	protected String MODE;

	@Value("#{jdbc['payco.driver']}")
	private String DB_DRIVER_PAYCO;
	@Value("#{jdbc['payco.url']}")
	private String DB_URL_PAYCO;
	@Value("#{jdbc['payco.username']}")
	private String DB_USERNAME_PAYCO;
	@Value("#{jdbc['payco.password']}")
	private String DB_PASSWORD_PAYCO;

	@Value("#{jdbc['devpayco.driver']}")
	private String DB_DRIVER_DEVPAYCO;
	@Value("#{jdbc['devpayco.url']}")
	private String DB_URL_DEVPAYCO;
	@Value("#{jdbc['devpayco.username']}")
	private String DB_USERNAME_DEVPAYCO;
	@Value("#{jdbc['devpayco.password']}")
	private String DB_PASSWORD_DEVPAYCO;

	
	@Bean
	public BasicDataSource dataSourcePayco() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		/**
		 * 2015-09-24 gupark
		 * conf.properties MODE 가 PAYCO 일경우 JDBC는 payco를 바라본다.
		 * 이는 mapper마다 resources name을 변경해주는 것이 귀찮기 때문에 datasource에서 분기처리한다.
		 */
		if ("PAYCO".equals(MODE)) {
			dataSource.setDriverClassName(DB_DRIVER_PAYCO);
			dataSource.setUrl(AES.aesDecodeStr(DB_URL_PAYCO, CryptoStr));
			dataSource.setUsername(AES.aesDecodeStr(DB_USERNAME_PAYCO, CryptoStr));
			dataSource.setPassword(AES.aesDecodeStr(DB_PASSWORD_PAYCO, CryptoStr));
		} else {
			dataSource.setDriverClassName(DB_DRIVER_DEVPAYCO);
			dataSource.setUrl(AES.aesDecodeStr(DB_URL_DEVPAYCO, CryptoStr));
			dataSource.setUsername(AES.aesDecodeStr(DB_USERNAME_DEVPAYCO, CryptoStr));
			dataSource.setPassword(AES.aesDecodeStr(DB_PASSWORD_DEVPAYCO, CryptoStr));
		}
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(300);
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryPayco() throws Exception {
		// 여기도 위와 같은 이유로 분기처리
		String sqlMapperPath = "classpath:/config/sqlmap/**/*PaycoDev.xml";
		if ("PAYCO".equals(MODE))
			sqlMapperPath = "classpath:/config/sqlmap/**/*Payco.xml";

		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSourcePayco());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(sqlMapperPath));
		sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/config/sqlmap/sql-map-config.xml"));
		return sqlSessionFactory;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionPayco() throws Exception{
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryPayco().getObject());
		sqlSession.clearCache();
		return sqlSession;
	}
	
	@Bean
	public DataSourceTransactionManager txManagerPayco() throws Exception {
		return new DataSourceTransactionManager(dataSourcePayco());
	}
}
