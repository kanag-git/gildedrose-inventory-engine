package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public final class StandardItemAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        val degrade = (item.isExpired()) ? 2 : 1;
        item.degradeQualityBy(degrade);
        item.clampQualityBounds(0, 50);
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.STANDARD;
    }
}
