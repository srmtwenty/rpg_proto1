package com.scott.controllers;


import com.scott.models.*;
import com.scott.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/players")
@CrossOrigin(origins="http://localhost:3000")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    EnemyRepository enemyRepository;
    @PostMapping("/create")
    public Player createPlayer(@RequestBody Player player){
        return playerRepository.save(player);
    }
    @GetMapping
    public List<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Player findPlayerById(@PathVariable Long id){
        return playerRepository.findById(id).orElseThrow(()->new RuntimeException("User has not been found!"));
    }

    @PutMapping("/{id}/update")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player){
        Player updateP=this.findPlayerById(id);
        updateP.setName(player.getName());
        updateP.setAge(player.getAge());
        updateP.setGender(player.getGender());
        updateP.setHp(player.getMaxHp());
        updateP.setMp(player.getMaxMp());
        updateP.setMaxHp(player.getMaxHp());
        updateP.setMaxMp(player.getMaxMp());
        updateP.setStrength(player.getStrength());
        updateP.setDefense(player.getDefense());
        updateP.setMoney(player.getMoney());
        return playerRepository.save(updateP);
    }

    @DeleteMapping("/{id}/delete")
    public void deletePlayer(@PathVariable Long id){
        playerRepository.deleteById(id);
    }

    @PutMapping("/{id}/equipA/{armorId}")
    public Player equipPlayerArmor(@PathVariable Long id, @PathVariable Long armorId){
        Player updateP=playerRepository.findById(id).get();
        Armor armor=armorRepository.findById(armorId).get();
        updateP.equipArmor(armor);
        updateP.setTotalDefense(updateP.getDefense()+armor.getDefense());
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeA/{armorId}")
    public Player removePlayerArmor(@PathVariable Long id, @PathVariable Long armorId){
        Player updateP=playerRepository.findById(id).get();
        Armor armor=armorRepository.findById(armorId).get();

        updateP.setTotalDefense(updateP.getTotalDefense()-armor.getDefense());
        updateP.removeArmor(armor);
        return playerRepository.save(updateP);
    }


    @PutMapping("/{id}/equipW/{weaponId}")
    public Player equipPlayerWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Player updateP=playerRepository.findById(id).get();
        Weapon weapon=weaponRepository.findById(weaponId).get();
        updateP.equipWeapon(weapon);
        updateP.setTotalStrength(updateP.getStrength()+weapon.getStrength());
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeW/{weaponId}")
    public Player removePlayerWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Player updateP=playerRepository.findById(id).get();
        Weapon weapon=weaponRepository.findById(weaponId).get();
        updateP.setTotalStrength(updateP.getTotalStrength()-weapon.getStrength());
        updateP.removeWeapon(weapon);

        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/assignNation/{nationId}")
    public Player assignNation(@PathVariable Long id, @PathVariable Long nationId){
        Player updateP=playerRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        updateP.setNation(nation);
        System.out.println("updated!");
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeNation/{nationId}")
    public Player removeNation(@PathVariable Long id, @PathVariable Long nationId){
        Player updateP=playerRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        updateP.removeNation(nation);
        System.out.println("updated!");
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/betting")
    public Player betting(@PathVariable Long id){
        Player updateP=playerRepository.findById(id).get();
        int number=(int)(Math.random()*(500 - -500)+ -500);
        System.out.println(number);
        updateP.setMoney(updateP.getMoney()-200 + number);

        if(updateP.getMoney()<0){
            updateP.setMoney(0);
        }
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/battle/{enemyId}")
    public Player battleOneTurn(@PathVariable Long id, @PathVariable Long enemyId) {
        Player updateP = playerRepository.findById(id).get();
        Enemy updateE = enemyRepository.findById(enemyId).get();


            System.out.println("Player attacks!");
            updateE.setHp(updateE.getHp() - updateE.getDefense() - updateP.getTotalStrength());
            enemyRepository.save(updateE);
            if (updateE.getHp() <= 0) {
                updateE.setHp(0);
                System.out.println("Enemy HP:" + updateE.getHp());
                System.out.println("You win!");
                updateP.setMoney(updateE.getMoney() + updateE.getMoney());
                System.out.println("You earned " + updateE.getMoney());
                System.out.println("Game Set");
            }

            System.out.println("Enemy HP:" + updateE.getHp());

        if (updateE.getHp() > 0) {
            System.out.println("Enemy attacks!");
            updateP.setHp(updateP.getHp() - updateP.getTotalDefense() - updateE.getStrength());
            playerRepository.save(updateP);
            if (updateP.getHp() <= 0) {
                updateP.setHp(0);
                System.out.println("Player HP:" + updateP.getHp());
                System.out.println("You lose!");
                System.out.println("Game Set");
            }
            System.out.println("Player HP:" + updateP.getHp());
        }
        enemyRepository.save(updateE);
        return playerRepository.save(updateP);
    }
    @PutMapping("/{id}/recovery")
    public Player fullRecovery(@PathVariable Long id){
        Player updateP=this.findPlayerById(id);
        updateP.setHp(updateP.getMaxHp());
        return playerRepository.save(updateP);
    }
}