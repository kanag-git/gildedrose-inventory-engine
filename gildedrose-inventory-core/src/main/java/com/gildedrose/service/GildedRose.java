package com.gildedrose.service;

import com.gildedrose.agingpolicy.ItemAgingPolicy;
import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;

import static com.gildedrose.agingpolicy.ItemAgingPolicyFactory.AGING_POLICY;

public class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            AgingItem agingItem = new AgingItem(item);
            ItemAgingPolicy policy = AGING_POLICY.getPolicy(agingItem.getCategory());
            policy.age(agingItem);
        }
    }
}
