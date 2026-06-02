package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.AGED_BRIE;

@Component
public class AgedBrieAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        item.improveQualityBy(1);

        item.passOneDay();

        if (item.isExpired()) {
            item.improveQualityBy(1);
        }

        item.clampQualityBounds(0,50);
    }

    @Override
    public ItemCategory getCategory() {
        return AGED_BRIE;
    }
}
