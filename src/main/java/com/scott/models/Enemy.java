package com.scott.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scott.models.Item;
import com.scott.models.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enemy implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int hp;

    private int maxHp;

    private int mp;

    private int maxMp;

    private int strength;

    private int totalStrength;

    private int defense;

    private int totalDefense;

    private int money;

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="enemies_items",
            joinColumns=@JoinColumn(name="enemy_id"),
            inverseJoinColumns=@JoinColumn(name="item_id")
    )
    private Set<Item> items;

    @JsonIgnore
    @ManyToMany(mappedBy="enemies")
    private Set<Player> players=new HashSet<>();
}
