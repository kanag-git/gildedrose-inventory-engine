package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;

public final class NoOperationalItemAgingPolicy implements ItemAgingPolicy {

    @Override
    public void age(final AgingItem item) {
    }

    @Override
    public ItemCategory getCategory() {
        return null;
    }
}
