package com.news.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public enum UserRole {
    ROLE_NORMAL, ROLE_CONTENT_WRITER, ROLE_ADMIN;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.name()));
    }
}
