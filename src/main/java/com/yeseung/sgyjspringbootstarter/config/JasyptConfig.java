package com.yeseung.sgyjspringbootstarter.config;

import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class JasyptConfig {

    private final JasyptEncryptorConfigurationProperties configurationProperties;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(Environment environment) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(environment.getProperty("jasypt.encryptor.password", configurationProperties.getPassword()));
        config.setAlgorithm(configurationProperties.getAlgorithm());
        config.setKeyObtentionIterations(configurationProperties.getKeyObtentionIterations());
        config.setPoolSize(configurationProperties.getPoolSize());
        config.setProvider(new BouncyCastleProvider());
        config.setSaltGeneratorClassName(configurationProperties.getSaltGeneratorClassname());
        config.setStringOutputType(configurationProperties.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }

}