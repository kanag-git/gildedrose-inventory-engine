package com.gildedrose.model;

import java.util.HashMap;
import java.util.Map;

public enum ItemCategory {
    AGED_BRIE("Aged Brie"),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    CONJURED("Conjured Mana Cake"),
    STANDARD("Standard");

    private static final Map<String, ItemCategory> REGISTRY = new HashMap<>();

    static {
        for (ItemCategory category : values()) {
            if (category != STANDARD) {
                REGISTRY.put(category.getName(), category);
            }
        }
    }

    private final String name;

    ItemCategory(String name) {
        this.name = name;
    }

    public static ItemCategory fromItemName(String itemName) {
        if (itemName == null) {
            return STANDARD;
        }
        return REGISTRY.getOrDefault(itemName, STANDARD);
    }

    public String getName() {
        return name;
    }
}
