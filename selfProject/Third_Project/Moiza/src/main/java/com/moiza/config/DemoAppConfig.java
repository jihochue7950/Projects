package com.moiza.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration //설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션이다.
@EnableWebMvc //이부분 MVC
@EnableTransactionManagement
@ComponentScan(basePackages="com.moiza") //구성요소를 스캔하겠다
@PropertySource("classpath:persistence-mysql.properties")  //디비설정과 연결하곘다
public class DemoAppConfig  implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean //@Configuration과 함께 사용하며, 자바클래스 내에 그 종속성을 매스드화(인스턴스화)하는데 사용한다
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;	
	}
	
	@Bean
	public DataSource myDataSource() {
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();
		
		//jdbc 커넥션 풀설정
		try {
			myDataSource.setDriverClass(env.getProperty("jdbc.driver"));	
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		logger.info(">>>>>jdbc.url===" + env.getProperty("jdbc.driver"));
		logger.info(">>>>>jdbc.url===" + env.getProperty("jdbc.url"));
		
		
		myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDataSource.setUser(env.getProperty("jdbc.user"));
		myDataSource.setPassword(env.getProperty("jdbc.password"));
		
		myDataSource.setInitialPoolSize(getInt("connection.pool.initialPoolSize"));
		myDataSource.setMinPoolSize(getInt("connection.pool.minPoolSize"));
		myDataSource.setMaxPoolSize(getInt("connection.pool.maxPoolSize"));
		myDataSource.setMaxIdleTime(getInt("connection.pool.maxIdleTime"));
		
		return myDataSource;
	
	}
	
	private Properties getHibernateProperties() {

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return props;				
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}	
	
	private int getInt(String st) {
		int in = Integer.parseInt(env.getProperty(st));
		return in; 		
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	}
}
