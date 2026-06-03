package com.gildedrose.database.repositories;

import com.gildedrose.database.enities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
