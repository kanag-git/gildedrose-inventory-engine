package com.gildedrose.service;

import com.gildedrose.agingpolicy.AgedBrieAgingPolicy;
import com.gildedrose.agingpolicy.BackstagePassesAgingPolicy;
import com.gildedrose.agingpolicy.ConjuredItemAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicyRegistry;
import com.gildedrose.agingpolicy.ItemAgingPolicySettings;
import com.gildedrose.agingpolicy.StandardItemAgingPolicy;
import com.gildedrose.model.Item;
import lombok.val;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GildedRoseApprovalTest {

    private GildedRoseInventoryAgingService gildedRoseInventoryAgingPort;

    @BeforeEach
    void setUp() {
        val standardPolicySettings = new ItemAgingPolicySettings.StandardPolicySettings(1, 2, 0, 50);
        val agedBriePolicySettings = new ItemAgingPolicySettings.AgedBriePolicySettings(1, 2, 0, 50);

        val backstagePassTierFor5Days = new ItemAgingPolicySettings.AgingTier(5, 3);
        val backstagePassTierFor10Days = new ItemAgingPolicySettings.AgingTier(10, 2);
        val backstagePassPolicySettings = new ItemAgingPolicySettings.BackstagePassPolicySettings(1, List.of(backstagePassTierFor5Days, backstagePassTierFor10Days), 0, 50);

        val conjuredPolicySettings = new ItemAgingPolicySettings.ConjuredPolicySettings(2, 4, 0, 50);

        val testPolicies = List.of(
                new StandardItemAgingPolicy(standardPolicySettings),
                new AgedBrieAgingPolicy(agedBriePolicySettings),
                new BackstagePassesAgingPolicy(backstagePassPolicySettings),
                new ConjuredItemAgingPolicy(conjuredPolicySettings));

        val itemAgingPolicyRegistry = new ItemAgingPolicyRegistry(testPolicies);

        this.gildedRoseInventoryAgingPort = new GildedRoseInventoryServiceImpl(itemAgingPolicyRegistry);
    }

    @Test
    public void verifyThirtyDaysOfInventoryUpdates() {
        // Arrange
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Conjured Mana Cake", 3, 6) };


        StringBuilder textLedger = new StringBuilder();

        //Act for 30 days
        int days = 30;
        for (int day = 0; day <= days; day++) {
            textLedger.append("-------- day ").append(day).append(" --------\n");
            textLedger.append("name, sellIn, quality\n");

            for (Item item : items) {
                textLedger.append(item.toString()).append("\n");
            }
            textLedger.append("\n");

            gildedRoseInventoryAgingPort.ageInventory(List.of(items));
        }

        // Assert
        Approvals.verify(textLedger.toString());
    }
}
