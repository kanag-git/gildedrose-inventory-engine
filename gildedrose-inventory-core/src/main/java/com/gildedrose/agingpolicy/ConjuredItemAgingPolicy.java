package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public final class ConjuredItemAgingPolicy implements ItemAgingPolicy {
    private final ItemAgingPolicySettings.ConjuredPolicySettings conjuredPolicySettings;

    public ConjuredItemAgingPolicy(final ItemAgingPolicySettings.ConjuredPolicySettings conjuredPolicySettings) {
        this.conjuredPolicySettings = conjuredPolicySettings;
    }

    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        val degrade = (item.isExpired()) ? conjuredPolicySettings.expiredDegradeRate() : conjuredPolicySettings.degradeRate();
        item.degradeQualityBy(degrade);
        item.clampQualityBounds(conjuredPolicySettings.minQuality(), conjuredPolicySettings.maxQuality());
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.CONJURED;
    }
}
