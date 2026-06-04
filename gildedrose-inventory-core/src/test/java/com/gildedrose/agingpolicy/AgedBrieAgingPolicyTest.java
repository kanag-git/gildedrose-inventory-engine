package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgedBrieAgingPolicyTest {

    private final ItemAgingPolicySettings.AgedBriePolicySettings agedBriePolicySettings
            = new ItemAgingPolicySettings.AgedBriePolicySettings(1, 2, 0, 50);

    private final  AgedBrieAgingPolicy agedBrieAgingPolicy = new AgedBrieAgingPolicy(agedBriePolicySettings);

    @Test
    @DisplayName("When sellIn day passes, Then quality increases by 1")
    void shouldQualityIncreasesWhenOlder() {
        Item baseItem = new Item("Aged Brie", 5, 20);
        AgingItem item = new AgingItem(baseItem);

        agedBrieAgingPolicy.age(item);

        assertEquals(4, item.getDaysRemaining());
        assertEquals(21, item.getQuality());
    }

    @Test
    @DisplayName("When expired, Then quality increases by 2")
    void shouldQualityIncreasesWhenExpired() {
        Item baseItem = new Item("Aged Brie", 0, 20);
        AgingItem item = new AgingItem(baseItem);

        agedBrieAgingPolicy.age(item);

        assertEquals(-1, item.getDaysRemaining());
        assertEquals(22, item.getQuality());
    }

    @Test
    @DisplayName("Return Aged brie category")
    void shouldReturnAgedBrieCategory() {
        assertEquals(ItemCategory.AGED_BRIE, agedBrieAgingPolicy.getCategory());
    }
}