package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.SULFURAS;

@Component
public class SulfurasItemAgingPolicy implements ItemAgingPolicy {
    @Override
    public void age(final AgingItem item) {
    }

    @Override
    public ItemCategory getCategory() {
        return SULFURAS;
    }
}
