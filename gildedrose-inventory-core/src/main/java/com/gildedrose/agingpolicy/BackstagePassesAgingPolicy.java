package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.BACKSTAGE_PASSES;

@Component
public final class BackstagePassesAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        val days = item.getDaysRemaining();

        if (days < 5) {
            item.improveQualityBy( 3);
        } else if (days < 10) {
            item.improveQualityBy(2);
        } else {
            item.improveQualityBy(1);
        }

        if (item.isExpired()) {
            item.dropQualityToMin();
        }

        item.clampQualityBounds(0, 50);
    }

    @Override
    public ItemCategory getCategory() {
        return BACKSTAGE_PASSES;
    }
}
