package com.scott.controllers;

import com.scott.models.Armor;
import com.scott.repositories.ArmorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armors")
@CrossOrigin(origins="http://localhost:3000")
public class ArmorController {
    @Autowired
    ArmorRepository armorRepository;

    @PostMapping("/create")
    public Armor createArmor(@RequestBody Armor armor){
        return armorRepository.save(armor);
    }

    @GetMapping
    public List<Armor> findAllArmors(){
        return armorRepository.findAll();
    }

    @GetMapping("/{armorId}")
    public Armor findArmorById(@PathVariable Long armorId){
        return armorRepository.findById(armorId).orElseThrow(()->new RuntimeException("Armor has not been found!"));
    }

    @PutMapping("/{armorId}/update")
    public Armor updateArmor(@PathVariable Long armorId, @RequestBody Armor armor){
        Armor updateA=this.findArmorById(armorId);
        updateA.setName(armor.getName());
        updateA.setPrice(armor.getPrice());
        updateA.setDefense(armor.getDefense());
        updateA.setDescription(armor.getDescription());
        return armorRepository.save(updateA);
    }

    @DeleteMapping("/{armorId}/delete")
    public void deleteArmor(@PathVariable Long armorId){
        armorRepository.deleteById(armorId);
    }
}
