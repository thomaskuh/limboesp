package net.limbomedia.esp;

import javax.sql.DataSource;

import org.kuhlins.binstore.BinStore;
import org.kuhlins.binstore.jpa.Bin;
import org.kuhlins.binstore.jpa.BinStoreDb;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackageClasses = { PersistenceConfig.class, Bin.class })
public class PersistenceConfig {

	@Bean
	public BinStore beanBinStore(DataSource dataSource) {
		return new BinStoreDb(dataSource);
	}

}
