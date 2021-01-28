package com.bug_tracker.bug_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    //@NotBlank(message = "Username is required")
    private String username;
    //@NotBlank(message = "Password is required")
    private String password;
    //@Email
    //@NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;
    @OneToMany(mappedBy = "creator")
    private List<Project> projects;

}
