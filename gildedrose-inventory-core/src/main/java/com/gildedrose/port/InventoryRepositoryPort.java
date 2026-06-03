package com.gildedrose.port;

import com.gildedrose.model.Item;

import java.util.List;

public interface InventoryRepositoryPort {
    void saveAll(List<Item> items);
}
