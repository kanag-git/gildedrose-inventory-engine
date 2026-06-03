package com.gildedrose.agingpolicy;

import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public final class ItemAgingPolicyRegistry {

    private final Map<ItemCategory, ItemAgingPolicy> policyRegistry;

    public ItemAgingPolicyRegistry(List<ItemAgingPolicy> policies) {
        this.policyRegistry = policies.stream()
                                      .collect(Collectors.toMap(ItemAgingPolicy::getCategory, Function.identity()));
    }

    public ItemAgingPolicy getPolicyFor(ItemCategory category) {
        return policyRegistry.getOrDefault(category, policyRegistry.get(ItemCategory.STANDARD));
    }
}
