package org.carpetati.spring.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class ThymeleafDilectConfig {
	// Bean para utilizar el thymeleaf paginator automatico
	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}

	/* Esta configuracion sirve para obtener la connection y pasarla al jasperreport
	 * @Bean
	 * 
	 * @ConfigurationProperties(prefix = "spring.datasource") public DataSource
	 * public DataSource getDataSource() { DataSourceBuilder<?> ds =
	 * DataSourceBuilder.create(); ds.username("root"); ds.password("Admin2013");
	 * ds.url("jdbc:mysql://localhost:3306/carpetativ3");
	 * ds.driverClassName("org.mariadb.jdbc.Driver"); return ds.build(); }
	 */
}
