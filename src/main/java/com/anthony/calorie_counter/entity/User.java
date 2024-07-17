package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_users")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phoneNumber;
    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String password, String email, String phoneNumber, Set<Role> roles) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public User(String fullName, String password, String email, String phoneNumber) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).toList();
    }

    @Override
    public String getUsername() {
        return email;
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
}
