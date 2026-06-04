package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardItemAgingPolicyTest {
    private final ItemAgingPolicySettings.StandardPolicySettings standardPolicySettings
            = new ItemAgingPolicySettings.StandardPolicySettings(1, 2, 0, 50);

    private final StandardItemAgingPolicy standardItemAgingPolicy = new StandardItemAgingPolicy(standardPolicySettings);

    @Test
    @DisplayName("When sellIn day passes and quality is greater than zero, Then quality decreases by 1")
    void shouldQualityGreaterThanZeroDecreasesByOneWhenDayPasses() {
        Item baseItem = new Item("Standard Item", 10, 20);
        AgingItem item = new AgingItem(baseItem);

        standardItemAgingPolicy.age(item);

        assertEquals(9, item.getDaysRemaining());
        assertEquals(19, item.getQuality());
    }

    @Test
    @DisplayName("When expired, Then quality decreases by two")
    void shouldQualityDecreasedByTwoWhenExpired() {
        Item baseItem = new Item("Standard Item", 0, 20);
        AgingItem item = new AgingItem(baseItem);

        standardItemAgingPolicy.age(item);

        assertEquals(-1, item.getDaysRemaining());
        assertEquals(18, item.getQuality());
    }

    @Test
    @DisplayName("When sellIn day passes any item, Then quality should never drop below zero")
    void shouldQualityNeverDropsBelowZeroForAnyItems() {
        Item baseItem = new Item("Standard Item", 5, 0);
        AgingItem item = new AgingItem(baseItem);

        standardItemAgingPolicy.age(item);

        assertEquals(0, item.getQuality());
    }

    @Test
    @DisplayName("Return Standard category")
    void shouldReturnStanardCategory() {
        assertEquals(ItemCategory.STANDARD, standardItemAgingPolicy.getCategory());
    }
}
