package io.agileinteligence.ppmtool.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Proxy(lazy = false)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "ユーザー名はメールである必要があります")
    @NotBlank(message = "必須項目です")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "必須項目です")
    private String fullName;

    @NotBlank(message = "必須項目です")
    private String password;

    @Transient
    private String confirmPassword;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public User() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getConfirmPassword() {
	return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
    }

    public List<Project> getProjects() {
	return projects;
    }

    public void setProjects(List<Project> projects) {
	this.projects = projects;
    }

    @PrePersist
    protected void onCreate() {
	this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
	this.updatedAt = new Date();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
	return true;
    }
}
