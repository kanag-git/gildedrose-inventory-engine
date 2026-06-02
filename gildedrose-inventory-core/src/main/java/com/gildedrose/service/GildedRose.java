package com.gildedrose.service;

import com.gildedrose.model.Item;
import com.gildedrose.model.ItemCategory;

import static com.gildedrose.model.ItemCategory.AGED_BRIE;
import static com.gildedrose.model.ItemCategory.BACKSTAGE_PASSES;
import static com.gildedrose.model.ItemCategory.SULFURAS;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!AGED_BRIE.getName().equals(item.name)
                    && !BACKSTAGE_PASSES.getName().equals(item.name)) {
                if (item.quality > 0) {
                    if (!SULFURAS.getName().equals(item.name)) {
                        item.quality = item.quality - 1;
                    }
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (BACKSTAGE_PASSES.getName().equals(item.name)) {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1;
                            }
                        }
                    }
                }
            }

            if (!SULFURAS.getName().equals(item.name)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!AGED_BRIE.getName().equals(item.name)) {
                    if (!BACKSTAGE_PASSES.getName().equals(item.name)) {
                        if (item.quality > 0) {
                            if (!SULFURAS.getName().equals(item.name)) {
                                item.quality = item.quality - 1;
                            }
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }
}
