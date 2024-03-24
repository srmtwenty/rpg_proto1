package com.scott.repositories;

import com.scott.models.Inventory;
import com.scott.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {



}
