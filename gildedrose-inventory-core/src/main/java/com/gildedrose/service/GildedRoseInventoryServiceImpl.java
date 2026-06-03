package com.gildedrose.service;

import com.gildedrose.agingpolicy.ItemAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicyRegistry;
import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;

import java.util.List;

public class GildedRoseInventoryServiceImpl implements GildedRoseInventoryAgingService {

    private final ItemAgingPolicyRegistry itemAgingPolicyRegistry;

    public GildedRoseInventoryServiceImpl(final ItemAgingPolicyRegistry itemAgingPolicyRegistry) {
        this.itemAgingPolicyRegistry = itemAgingPolicyRegistry;
    }

    @Override
    public void ageInventory(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        for (Item item : items) {
            AgingItem agingItem = new AgingItem(item);
            ItemAgingPolicy policy = itemAgingPolicyRegistry.getPolicyFor(agingItem.getCategory());
            policy.age(agingItem);
        }
    }
}
