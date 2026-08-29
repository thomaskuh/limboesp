package net.limbomedia.esp;

import javax.sql.DataSource;

import org.kuhlins.lib.utils.binstore.BinStore;
import org.kuhlins.lib.utils.binstore.jpa.string.BinStoreDbString;
import org.kuhlins.lib.utils.binstore.jpa.string.BinString;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackageClasses = {PersistenceConfig.class, BinString.class})
public class PersistenceConfig {

    @Bean
    public BinStore beanBinStore(DataSource dataSource) {
        return new BinStoreDbString(dataSource);
    }
}
