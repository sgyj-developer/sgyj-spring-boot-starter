package com.yeseung.sgyjspringbootstarter.aware;

import org.springframework.data.domain.AuditorAware;

public interface LoginAuditorAware<T> extends AuditorAware<T> {

}