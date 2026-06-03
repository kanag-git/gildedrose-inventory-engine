package com.gildedrose.service;

import com.gildedrose.model.Item;

import java.util.List;

public interface GildedRoseInventoryAgingService {
    void ageInventory(List<Item> items);
}
