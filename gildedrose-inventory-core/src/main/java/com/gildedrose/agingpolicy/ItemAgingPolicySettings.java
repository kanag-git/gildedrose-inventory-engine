package com.gildedrose.agingpolicy;

public record ItemAgingPolicySettings(
        StandardPolicySettings standardPolicySettings,
        AgedBriePolicySettings agedBriePolicySettings,
        BackstagePassPolicySettings backstagePassPolicySettings
) {
    public ItemAgingPolicySettings {}

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
