package com.cse19.ue.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Title title;
    @Column(nullable = false)
    private String name;
    private String phoneNumber;
    @Column(nullable = false)
    private String NIC;
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private String inviterIndex;
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}
