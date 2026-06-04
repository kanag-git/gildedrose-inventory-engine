package com.gildedrose.agingpolicy;

import com.gildedrose.model.AgingItem;
import com.gildedrose.model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoOperationalItemAgingPolicyTest {

    private final NoOperationalItemAgingPolicy noOperationalItemAgingPolicy = new NoOperationalItemAgingPolicy();

    @Test
    @DisplayName("When Sulfuras, then completely ignore aging and quality update")
    void shouldCompletelyIgnoreAgingAndQualityUpdate() {
        Item baseItem = new Item("Sulfuras, Hand of Ragnaros", 80, 80);
        AgingItem legendaryItem = new AgingItem(baseItem);

        noOperationalItemAgingPolicy.age(legendaryItem);

        assertEquals(80, legendaryItem.getDaysRemaining());
        assertEquals(80, legendaryItem.getQuality());
    }

    @Test
    @DisplayName("Return null for NoOperationalItemAgingPolicy")
    void shouldReturnNullCategoryAsPerTheImplementation() {
        assertNull(noOperationalItemAgingPolicy.getCategory());
    }
}
