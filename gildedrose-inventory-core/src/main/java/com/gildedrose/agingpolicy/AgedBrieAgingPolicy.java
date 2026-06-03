package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.ItemCategory;
import org.springframework.stereotype.Component;

import static com.gildedrose.model.ItemCategory.AGED_BRIE;

@Component
public final class AgedBrieAgingPolicy implements ItemAgingPolicy {
    private final ItemAgingPolicySettings.AgedBriePolicySettings agedBriePolicySettings;

    public AgedBrieAgingPolicy(final ItemAgingPolicySettings.AgedBriePolicySettings agedBriePolicySettings) {
        this.agedBriePolicySettings = agedBriePolicySettings;
    }

    @Override
    public void age(AgingItem item) {
        item.passOneDay();
        int improve = (item.isExpired()) ? agedBriePolicySettings.expiredImprovementRate() : agedBriePolicySettings.improvementRate();
        item.improveQualityBy(improve);
        item.clampQualityBounds(agedBriePolicySettings.minQuality(), agedBriePolicySettings.maxQuality());
    }

    @Override
    public ItemCategory getCategory() {
        return AGED_BRIE;
    }
}
