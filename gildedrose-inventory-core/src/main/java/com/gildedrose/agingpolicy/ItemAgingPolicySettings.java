package com.gildedrose.agingpolicy;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gildedrose.item-aging.policy")
public record ItemAgingPolicySettings(
        StandardPolicySettings standard,
        AgedBriePolicySettings agedBrie,
        BackstagePassPolicySettings backstagePasses,
        ConjuredPolicySettings conjured
) {

    public record StandardPolicySettings (
            int degradeRate,
            int expiredDegradeRate,
            int minQuality,
            int maxQuality
    ){}

    public record AgedBriePolicySettings(
            int improvementRate,
            int expiredImprovementRate,
            int minQuality,
            int maxQuality
    ) {
    }
    public record BackstagePassPolicySettings(
            int baseRate,
            int doubleQualityIncreaseDayRange,
            int tripleQualityIncreaseDayRange,
            int minQuality,
            int maxQuality
    ) {
    }

    public record ConjuredPolicySettings (
            int degradeRate,
            int expiredDegradeRate,
            int minQuality,
            int maxQuality
    ){}
}
