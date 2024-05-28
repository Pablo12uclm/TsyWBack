package edu.uclm.esi.juegos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.annotation.Nonnull;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // pk en la base de datos

    @Nonnull
    @Column(unique = true, nullable = false)
    private String email;

    @Nonnull
    @Column(unique = true, nullable = false)
    private String username;

    @Nonnull
    private String password;

    // Constructor por defecto
    public User() {}
    
    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
