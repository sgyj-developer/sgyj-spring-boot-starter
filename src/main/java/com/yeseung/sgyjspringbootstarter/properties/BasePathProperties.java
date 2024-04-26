package com.yeseung.sgyjspringbootstarter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("base.path")
public class BasePathProperties {

    private String uploadDir;

}
