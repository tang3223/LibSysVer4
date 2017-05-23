package com.gcit.libsys;

import org.apache.commons.dbcp2.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.*;

import com.gcit.libsystem.dao.*;

@Configuration
public class LibSysConf {
	
	public String driver = "com.mysql.cj.jdbc.Driver";
	public String url = "jdbc:mysql://localhost/library?verifyServerCertificate=false&useSSL=true";
	public String username = "root";
	public String password = "root";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager txManger(){
		DataSourceTransactionManager dsManager = new DataSourceTransactionManager();
		dsManager.setDataSource(dataSource());
		return dsManager;
	}
	
	@Bean
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AuthorDao authorDao(){
		return new AuthorDao();
	}
	
	@Bean
	public BookDao bookDao(){
		return new BookDao();
	}
	
	@Bean
	public GenreDao genreDao(){
		return new GenreDao();
	}
	
	@Bean
	public BranchDao branchDao(){
		return new BranchDao();
	}
	
	@Bean
	public PublisherDao publisherDao(){
		return new PublisherDao();
	}
	
	@Bean
	public BorrowerDao borrowerDao(){
		return new BorrowerDao();
	}
	
	@Bean
	public BookLoanDao bookLoanDao(){
		return new BookLoanDao();
	}
	
	@Bean
	public CopiesDao copiesDao(){
		return new CopiesDao();
	}
}






















