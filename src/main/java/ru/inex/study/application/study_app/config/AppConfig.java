package ru.inex.study.application.study_app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.inex.parameters.repository.BaseParametersRepository;
import ru.inex.parameters.service.BaseParametersService;
import ru.inex.study.application.study_app.service.ParameterService;


import java.time.Duration;


@Configuration
public class AppConfig {

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public RestTemplate restTemplate() {

        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    @Bean(name = "configDbDatasource", destroyMethod = "close")
    public HikariDataSource configDbDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("Config-DB");
        hikariConfig.setJdbcUrl(env.getProperty("config.db.url"));
        hikariConfig.setUsername(env.getProperty("config.db.login"));
        hikariConfig.setPassword(env.getProperty("config.db.password"));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setLeakDetectionThreshold(30000);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setConnectionTestQuery("select 1");
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "contentDbDataSource", destroyMethod = "close")
    public HikariDataSource contentDbDataSource(BaseParametersService baseParametersService) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("jdbc:postgresql")
                .host(baseParametersService.getParameterByBaseParameterPath("db.content.host"))
                .path(baseParametersService.getParameterByBaseParameterPath("db.content.database"))
                .port(Integer.parseInt(baseParametersService.getParameterByBaseParameterPath("db.content.port")))
                .build();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("Content-DB");
        hikariConfig.setJdbcUrl(uriComponents.toUriString());
        hikariConfig.setUsername(baseParametersService.getParameterByBaseParameterPath("db.content.login"));
        hikariConfig.setPassword(baseParametersService.getParameterByBaseParameterPath("db.content.password"));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setLeakDetectionThreshold(30000);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setConnectionTestQuery("select 1");
        return new HikariDataSource(hikariConfig);
    }



    @Bean
    public BaseParametersService baseParametersService(BaseParametersRepository baseParametersRepository) {
        return new ParameterService(baseParametersRepository, env.getProperty("cipher.key"));
    }

    @Bean
    public BaseParametersRepository baseParametersRepository() {
        return new BaseParametersRepository(new JdbcTemplate(configDbDataSource()));
    }


}