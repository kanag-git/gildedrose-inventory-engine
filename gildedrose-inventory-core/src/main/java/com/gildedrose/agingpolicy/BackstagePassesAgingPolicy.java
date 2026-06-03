package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.BACKSTAGE_PASSES;

@Component
public final class BackstagePassesAgingPolicy implements ItemAgingPolicy {
    private final ItemAgingPolicySettings.BackstagePassPolicySettings backstagePassPolicySettings;

    public BackstagePassesAgingPolicy(final ItemAgingPolicySettings.BackstagePassPolicySettings backstagePassPolicySettings) {
        this.backstagePassPolicySettings = backstagePassPolicySettings;
    }

    @Override
    public void age(AgingItem item) {
        val days = item.getDaysRemaining();

        if (days <= backstagePassPolicySettings.tripleQualityIncreaseDayRange()) {
            item.improveQualityBy(backstagePassPolicySettings.baseRate() * 3);
        } else if (days <= backstagePassPolicySettings.doubleQualityIncreaseDayRange()) {
            item.improveQualityBy(backstagePassPolicySettings.baseRate() * 2);
        } else {
            item.improveQualityBy(backstagePassPolicySettings.baseRate());
        }

        item.passOneDay();

        if (item.isExpired()) {
            item.dropQualityToMin();
        }

        item.clampQualityBounds(backstagePassPolicySettings.minQuality(), backstagePassPolicySettings.maxQuality());
    }

    @Override
    public ItemCategory getCategory() {
        return BACKSTAGE_PASSES;
    }
}
