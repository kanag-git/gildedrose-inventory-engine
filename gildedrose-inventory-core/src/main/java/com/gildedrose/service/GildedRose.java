package com.gildedrose.service;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import lombok.val;

import static com.gildedrose.model.ItemCategory.fromItemName;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            val agingItem = new AgingItem(item);
            switch (fromItemName(agingItem.getName())) {
                case SULFURAS -> {
                    continue;
                }
                case AGED_BRIE -> {
                    agingItem.passOneDay();
                    int valueToIncreaseQuality = (agingItem.isExpired()) ? 2 : 1;
                    agingItem.improveQualityBy(valueToIncreaseQuality);
                }
                case BACKSTAGE_PASSES -> {
                    agingItem.passOneDay();
                    if (agingItem.isExpired()) {
                        item.quality = 0;
                    } else if (agingItem.getDaysRemaining() < 5) {
                        agingItem.improveQualityBy(3);
                    } else if (agingItem.getDaysRemaining() < 10) {
                        agingItem.improveQualityBy(2);
                    } else {
                        agingItem.improveQualityBy(1);
                    }
                }
                case STANDARD -> {
                    agingItem.passOneDay();
                    int valueToDecreaseQuality = (agingItem.isExpired()) ? 2 : 1;
                    agingItem.degradeQualityBy(valueToDecreaseQuality);
                }
            }
            agingItem.clampQualityBounds(0, 50);
        }
    }
}
