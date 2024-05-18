package project.contcheck.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.contcheck.repositorys.UserRepository;
import project.contcheck.tenants.TenantIdentifierResolver;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Autowired
    TenantIdentifierResolver tenantIdentifierResolver;

    @Bean
    public Flyway flyway(DataSource dataSource){
        Flyway flyway = Flyway.configure()
                .locations("db/migration/default")
                .dataSource(dataSource)
                .schemas("default")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository, DataSource dataSource) {
        tenantIdentifierResolver.forceDefaultSchema();
        return args -> {
            repository.findAll().forEach(user -> {
                String tenant = user.getUsername();
                Flyway flyway = Flyway.configure()
                        .locations("db/migration/tenants")
                        .dataSource(dataSource)
                        .schemas(tenant)
                        .load();
                flyway.migrate();
            });
            tenantIdentifierResolver.resetForcedDefaultSchema();
        };
    }
}