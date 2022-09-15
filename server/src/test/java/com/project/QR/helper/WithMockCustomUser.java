package com.clone.stackoverflow.helper;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
  long memberId() default 1L;
  String email() default "hgd@gmail.com";
  String displayName() default "hgd";
  String password() default "1234";
  String role() default "ROLE_USER";
  String provider() default "local";
}
