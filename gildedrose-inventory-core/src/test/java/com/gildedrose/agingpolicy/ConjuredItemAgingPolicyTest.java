package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConjuredItemAgingPolicyTest {

    private final ItemAgingPolicySettings.ConjuredPolicySettings conjuredPolicySettings
            = new ItemAgingPolicySettings.ConjuredPolicySettings(2, 4, 0, 50);

    private final ConjuredItemAgingPolicy conjuredItemAgingPolicy = new ConjuredItemAgingPolicy(conjuredPolicySettings);

    @Test
    @DisplayName("When sellIn day passes and item is not expired, Then quality decreases by 2")
    void shouldDegradeTwiceAsFastAsStandardItems() {
        Item baseItem = new Item("Conjured Mana Cake", 10, 20);
        AgingItem item = new AgingItem(baseItem);

        conjuredItemAgingPolicy.age(item);

        assertEquals(9, item.getDaysRemaining());
        assertEquals(18, item.getQuality());
    }

    @Test
    @DisplayName("When expired, Then quality decreases twice as fast by 4")
    void shouldDegradeFourTimesAsFastWhenExpired() {
        Item baseItem = new Item("Conjured Mana Cake", 0, 20);
        AgingItem item = new AgingItem(baseItem);

        conjuredItemAgingPolicy.age(item);

        assertEquals(-1, item.getDaysRemaining());
        assertEquals(16, item.getQuality());
    }

    @Test
    @DisplayName("When sellIn day passes any item, Then quality should never drop below zero")
    void shouldQualityNeverDropsBelowZeroForAnyItems() {
        Item baseItem = new Item("Conjured Mana Cake", 5, 0);
        AgingItem item = new AgingItem(baseItem);

        conjuredItemAgingPolicy.age(item);

        assertEquals(0, item.getQuality());
    }

    @Test
    @DisplayName("Return Conjured category")
    void shouldReturnConjuredCategory() {
        assertEquals(ItemCategory.CONJURED, conjuredItemAgingPolicy.getCategory());
    }
}
