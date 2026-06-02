package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

@Component
public class StandardItemAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        if (item.hasQuality()) {
            item.degradeQualityBy(1);
        }

        item.passOneDay();

        if (item.isExpired() && item.hasQuality()) {
            item.degradeQualityBy(1);
        }

        item.clampQualityBounds(0, 50);
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.STANDARD;
    }
}
