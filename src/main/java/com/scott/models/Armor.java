package com.scott.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
//@Table(name="armors")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Armor implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int defense;

    private int price;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy="armor")
    private Set<Player> players=new HashSet<>();


}
