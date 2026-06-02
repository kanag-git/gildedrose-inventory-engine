package com.gildedrose.service;

import com.gildedrose.model.Item;

import static com.gildedrose.model.ItemCategory.fromItemName;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            switch (fromItemName(item.name)) {
                case SULFURAS -> {
                    continue;
                }
                case AGED_BRIE -> {
                    passOneDay(item);
                    int valueToIncreaseQuality = (item.sellIn < 0) ? 2 : 1;
                    improveQuality(item, valueToIncreaseQuality);
                }
                case BACKSTAGE_PASSES -> {
                    passOneDay(item);
                    if (item.sellIn < 0) {
                        item.quality = 0;
                    } else if (item.sellIn < 5) {
                        improveQuality(item, 3);
                    } else if (item.sellIn < 10) {
                        improveQuality(item, 2);
                    } else {
                        improveQuality(item, 1);
                    }
                }
                case STANDARD -> {
                    passOneDay(item);
                    int valueToDecreaseQuality = (item.sellIn < 0) ? 2 : 1;
                    degradeQuality(item, valueToDecreaseQuality);
                }
            }
            clampQuality(item);
        }
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

    private void clampQuality(Item item) {
        item.quality = Math.min(item.quality, 50);
        item.quality = Math.max(item.quality, 0);
    }
}
