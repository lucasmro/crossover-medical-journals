package com.crossover.medical.journals.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.crossover.medical.journals.core.UserRole;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface RestrictedTo {
    UserRole[] value() default UserRole.SUBSCRIBER;
}
