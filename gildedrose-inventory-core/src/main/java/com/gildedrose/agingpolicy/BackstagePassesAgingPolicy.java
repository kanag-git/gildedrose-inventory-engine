package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.BACKSTAGE_PASSES;

@Component
public class BackstagePassesAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        item.improveQualityBy(1);

        if (item.getDaysRemaining() <= 10) {
            item.improveQualityBy(1);
        }

        if (item.getDaysRemaining() <= 5) {
            item.improveQualityBy(1);
        }

        item.passOneDay();

        if (item.isExpired()) {
            item.ruinQuality();
        }

        item.clampQualityBounds(0, 50);
    }

    @Override
    public ItemCategory getCategory() {
        return BACKSTAGE_PASSES;
    }
}
