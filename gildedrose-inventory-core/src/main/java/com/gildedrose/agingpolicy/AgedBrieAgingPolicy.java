package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.AGED_BRIE;

@Component
final class AgedBrieAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        int improve = (item.isExpired()) ? 2 : 1;
        item.improveQualityBy(improve);
        item.clampQualityBounds(0,50);
    }

    @Override
    public ItemCategory getCategory() {
        return AGED_BRIE;
    }
}
