package com.ciel.pocket.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.UUID;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed
    private String username;

    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encodePassword(){
        this.setPassword(new BCryptPasswordEncoder().encode(this.getPassword()));
    }

    public User newUser(String username, String password){
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public User generateId(){
        this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
