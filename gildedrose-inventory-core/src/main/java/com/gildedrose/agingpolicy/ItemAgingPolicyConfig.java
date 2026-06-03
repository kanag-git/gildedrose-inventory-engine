package com.gildedrose.agingpolicy;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ItemAgingPolicySettings.class)
public class ItemAgingPolicyConfig {

    @Bean
    public ItemAgingPolicySettings.AgedBriePolicySettings agedBriePolicySettings(ItemAgingPolicySettings settings) {
        return settings.agedBrie();
    }

    @Bean
    public ItemAgingPolicySettings.StandardPolicySettings standardPolicySettings(ItemAgingPolicySettings settings) {
        return settings.standard();
    }

    @Bean
    public ItemAgingPolicySettings.BackstagePassPolicySettings backstagePassPolicySettings(ItemAgingPolicySettings settings) {
        return settings.backstagePasses();
    }

    @Bean
    public ItemAgingPolicySettings.ConjuredPolicySettings conjuredPolicySettings(ItemAgingPolicySettings settings) {
        return settings.conjured();
    }
}
