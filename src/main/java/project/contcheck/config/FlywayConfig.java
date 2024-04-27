package project.contcheck.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import project.contcheck.repositorys.UserRepository;

import javax.sql.DataSource;

public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource){
        Flyway flyway = Flyway.configure()
                .locations("db/migration/default")
                .dataSource(dataSource)
                .schemas(TenantIdentifierResolver.DEFAULT_TENANT)
                .load();
        flyway.migrate();
        return flyway;
    }


    // Nao esta funcionando, se colocar novas versoes no pacote tenants, ele so vao ser aplicados em usuarios novos criados, nos schemas dos antigos
    // a atualizaçao nao acontece.
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository, DataSource dataSource) {
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
        };
    }
}