package com.kesmarki.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ContactType type;

    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "address_Id")
    @JsonIgnore
    private Address address;
}
