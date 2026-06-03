package com.gildedrose.agingpolicy;

import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Component
public enum ItemAgingPolicyFactory {
    AGING_POLICY;

    private final Map<ItemCategory, ItemAgingPolicy> policyRegistry;

    ItemAgingPolicyFactory() {
        final Map<ItemCategory, ItemAgingPolicy> policyMap = new EnumMap<>(ItemCategory.class);

        add(policyMap, new StandardItemAgingPolicy());
        add(policyMap, new AgedBrieAgingPolicy());
        add(policyMap, new BackstagePassesAgingPolicy());
        add(policyMap, new SulfurasItemAgingPolicy());

        this.policyRegistry = Collections.unmodifiableMap(policyMap);
    }

    private void add(Map<ItemCategory, ItemAgingPolicy> map, ItemAgingPolicy policy) {
        map.put(policy.getCategory(), policy);
    }

    public ItemAgingPolicy getPolicy(ItemCategory category) {
        return policyRegistry.getOrDefault(category, policyRegistry.get(ItemCategory.STANDARD));
    }
}
