package com.apptool.user.config.dto;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UserDto {

    @NotBlank(message = "Le username est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être une adresse email valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    private Set<String> role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
      }
    
      public void setRole(Set<String> role) {
        this.role = role;
      }
}