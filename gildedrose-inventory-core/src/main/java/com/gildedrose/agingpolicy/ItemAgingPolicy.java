package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;

public interface ItemAgingPolicy {
    void age(AgingItem item);

    ItemCategory getCategory();
}
