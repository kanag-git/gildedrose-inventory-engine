package com.gildedrose.service;

import com.gildedrose.agingpolicy.ItemAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicyRegistry;
import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        items.stream()
             .map(AgingItem::new)
             .forEach(agingItem -> {
                 ItemAgingPolicy policy = itemAgingPolicyRegistry.getPolicyFor(agingItem.getCategory());
                 policy.age(agingItem);
             });
    }
}
