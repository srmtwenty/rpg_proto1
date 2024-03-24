package com.scott.controllers;

import com.scott.models.Inventory;
import com.scott.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@CrossOrigin(origins="http://localhost:3000")
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @PostMapping("/create")
    public Inventory createInventory(@RequestBody Inventory inventory){
        return inventoryRepository.save(inventory);
    }
    @GetMapping
    public List<Inventory> findAllInventories(){
        return inventoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Inventory findInventoryById(@PathVariable Long id){
        return inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Inventory has not been found!"));
    }

    @PutMapping("/{id}/update")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory){
        Inventory updateI=this.findInventoryById(id);
        updateI.setName(inventory.getName());
        updateI.setDescription(inventory.getDescription());
        updateI.setPlayer(inventory.getPlayer());
        return inventoryRepository.save(updateI);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteInventory(@PathVariable Long id){
        inventoryRepository.deleteById(id);
    }
}
