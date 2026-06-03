package com.gildedrose.agingpolicy;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
            List<AgingTier> tiers,
            int minQuality,
            int maxQuality
    ) {
    }

    public record AgingTier(
            int maxDaysRemaining,
            int multiplier
    ) {}

    public record ConjuredPolicySettings (
            int degradeRate,
            int expiredDegradeRate,
            int minQuality,
            int maxQuality
    ){}
}
