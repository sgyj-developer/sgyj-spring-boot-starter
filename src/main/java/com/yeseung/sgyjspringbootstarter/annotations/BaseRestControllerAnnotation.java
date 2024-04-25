package com.yeseung.sgyjspringbootstarter.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
public @interface BaseRestControllerAnnotation {

    @AliasFor(annotation = RestController.class)
    String value() default "";

    @AliasFor(annotation = RequestMapping.class)
    String path() default "";

}
