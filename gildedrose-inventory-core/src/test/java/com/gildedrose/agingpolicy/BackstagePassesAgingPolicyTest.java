package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackstagePassesAgingPolicyTest {

    private final ItemAgingPolicySettings.AgingTier backstagePassTierFor5Days = new ItemAgingPolicySettings.AgingTier(5, 3);
    private final ItemAgingPolicySettings.AgingTier backstagePassTierFor10Days = new ItemAgingPolicySettings.AgingTier(10, 2);
    private final ItemAgingPolicySettings.BackstagePassPolicySettings  backstagePassPolicySettings
            = new ItemAgingPolicySettings.BackstagePassPolicySettings(1, List.of(backstagePassTierFor5Days, backstagePassTierFor10Days), 0, 50);

    private final BackstagePassesAgingPolicy backstagePassesAgingPolicy = new BackstagePassesAgingPolicy(backstagePassPolicySettings);

    @Test
    @DisplayName("When sellIn day more than ten days, Then quality increases by 1")
    void shouldQualityIncreasesByOneWhenMoreThanTenDays() {
        Item baseItem = new Item("Backstage passes", 15, 20);
        AgingItem item = new AgingItem(baseItem);

        backstagePassesAgingPolicy.age(item);

        assertEquals(14, item.getDaysRemaining());
        assertEquals(21, item.getQuality());
    }

    @Test
    @DisplayName("When sellIn day six to ten days range, Then quality increases by 2")
    void shouldQualityIncreasesByTwoWhenSixToTenDaysRange() {
        Item baseItem = new Item("Backstage passes", 10, 20);
        AgingItem item = new AgingItem(baseItem);

        backstagePassesAgingPolicy.age(item);

        assertEquals(9, item.getDaysRemaining());
        assertEquals(22, item.getQuality());
    }

    @Test
    @DisplayName("When sellIn day one to five days range, Then quality increases by 3")
    void shouldQualityIncreasesByThreeWhenFiveToOneDayRange() {
        Item baseItem = new Item("Backstage passes", 5, 20);
        AgingItem item = new AgingItem(baseItem);

        backstagePassesAgingPolicy.age(item);

        assertEquals(4, item.getDaysRemaining());
        assertEquals(23, item.getQuality());
    }

    @Test
    @DisplayName("When expired, Drop quality to zero.")
    void shouldDropQualityToZeroWhenExpired() {
        Item baseItem = new Item("Backstage passes", 0, 20);
        AgingItem item = new AgingItem(baseItem);

        backstagePassesAgingPolicy.age(item);

        assertEquals(-1, item.getDaysRemaining());
        assertEquals(0, item.getQuality());
    }

    @Test
    @DisplayName("Return Backstage Passes category")
    void shouldReturnBackstagePassesCategory() {
        assertEquals(ItemCategory.BACKSTAGE_PASSES, backstagePassesAgingPolicy.getCategory());
    }
}
