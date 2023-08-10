package com.cse19.ue.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

//    @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
//    private List<Guest> guests;

//    @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
//    private List<UniversityEntryLog> entries;

}
