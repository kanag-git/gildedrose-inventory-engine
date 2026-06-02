package com.gildedrose.service;

import com.gildedrose.model.Item;

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
                if (isQualityGreaterThanZero(item)) {
                    if (!SULFURAS.getName().equals(item.name)) {
                        degradeQuality(item, 1);
                    }
                }
            } else {
                if (isQualityLessThanFifty(item)) {
                    improveQuality(item,1);

                    if (BACKSTAGE_PASSES.getName().equals(item.name)) {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                improveQuality(item,1);
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                improveQuality(item,1);
                            }
                        }
                    }
                }
            }

            if (!SULFURAS.getName().equals(item.name)) {
                passOneDay(item);
            }

            if (item.sellIn < 0) {
                if (!AGED_BRIE.getName().equals(item.name)) {
                    if (!BACKSTAGE_PASSES.getName().equals(item.name)) {
                        if (item.quality > 0) {
                            if (!SULFURAS.getName().equals(item.name)) {
                                degradeQuality(item, 1);
                            }
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (item.quality < 50) {
                        improveQuality(item,1);
                    }
                }
            }
        }
    }

    private boolean isQualityGreaterThanZero(Item item){
        return item.quality > 0;
    }

    private boolean isQualityLessThanFifty(Item item){
        return item.quality < 50;
    }

    private void improveQuality(Item item, int value) {
        item.quality = item.quality + value;
    }

    private void degradeQuality(Item item, int value) {
        item.quality = item.quality - value;
    }

    private void passOneDay(Item item) {
        item.sellIn = item.sellIn - 1;
    }
}
