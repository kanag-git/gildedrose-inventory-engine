package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public final class StandardItemAgingPolicy implements ItemAgingPolicy {
    private final ItemAgingPolicySettings.StandardPolicySettings standardPolicySettings;

    public StandardItemAgingPolicy(final ItemAgingPolicySettings.StandardPolicySettings standardPolicySettings) {
        this.standardPolicySettings = standardPolicySettings;
    }

    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        val degrade = (item.isExpired()) ? standardPolicySettings.expiredDegradeRate() : standardPolicySettings.degradeRate();
        item.degradeQualityBy(degrade);
        item.clampQualityBounds(standardPolicySettings.minQuality(), standardPolicySettings.maxQuality());
    }

    @Override
    public ItemCategory getCategory() {
        return ItemCategory.STANDARD;
    }
}
