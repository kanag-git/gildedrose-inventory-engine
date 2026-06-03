package com.gildedrose.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ItemCategory {
    AGED_BRIE("Aged Brie", false),
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert", false),
    SULFURAS("Sulfuras, Hand of Ragnaros", true),
    CONJURED("Conjured Mana Cake", false),
    STANDARD("Standard", false);

    private static final Map<String, ItemCategory> REGISTRY = new HashMap<>();

    static {
        for (ItemCategory category : values()) {
            if (category != STANDARD) {
                REGISTRY.put(category.getName(), category);
            }
        }
    }

    private final String name;
    private final boolean isSkipOperation;

    ItemCategory(String name,
                 boolean isSkipOperation) {
        this.name = name;
        this.isSkipOperation = isSkipOperation;
    }

    public static ItemCategory fromItemName(String itemName) {
        if (itemName == null) {
            return STANDARD;
        }
        return REGISTRY.getOrDefault(itemName, STANDARD);
    }

}
