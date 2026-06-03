package com.gildedrose.features.stepdefs;

import com.gildedrose.agingpolicy.AgedBrieAgingPolicy;
import com.gildedrose.agingpolicy.BackstagePassesAgingPolicy;
import com.gildedrose.agingpolicy.ConjuredItemAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicyRegistry;
import com.gildedrose.agingpolicy.ItemAgingPolicySettings;
import com.gildedrose.agingpolicy.StandardItemAgingPolicy;
import com.gildedrose.agingpolicy.SulfurasItemAgingPolicy;
import com.gildedrose.model.Item;
import com.gildedrose.service.GildedRoseInventoryAgingService;
import com.gildedrose.service.GildedRoseInventoryServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.val;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryAgingSteps {

    private final GildedRoseInventoryAgingService agingService;
    private List<Item> targetInventory;

    public InventoryAgingSteps() {
        val standardPolicySettings = new ItemAgingPolicySettings.StandardPolicySettings(1, 2, 0, 50);
        val agedBriePolicySettings = new ItemAgingPolicySettings.AgedBriePolicySettings(1, 2, 0, 50);

        val backstagePassTierFor5Days = new ItemAgingPolicySettings.AgingTier(5, 3);
        val backstagePassTierFor10Days = new ItemAgingPolicySettings.AgingTier(10, 2);
        val backstagePassPolicySettings = new ItemAgingPolicySettings.BackstagePassPolicySettings(1, List.of(backstagePassTierFor5Days, backstagePassTierFor10Days), 0, 50);

        val conjuredPolicySettings = new ItemAgingPolicySettings.ConjuredPolicySettings(2, 4, 0, 50);

        List<ItemAgingPolicy> strategies = List.of(
                new StandardItemAgingPolicy(standardPolicySettings),
                new AgedBrieAgingPolicy(agedBriePolicySettings),
                new BackstagePassesAgingPolicy(backstagePassPolicySettings),
                new SulfurasItemAgingPolicy(),
                new ConjuredItemAgingPolicy(conjuredPolicySettings));

        ItemAgingPolicyRegistry registry = new ItemAgingPolicyRegistry(strategies);
        this.agingService = new GildedRoseInventoryServiceImpl(registry);
    }

    @Given("an item named {string} with sellIn {int} and quality {int}")
    public void anItemNamedWithSellInAndQuality(String name, int sellIn, int quality) {
        this.targetInventory = List.of(new Item(name, sellIn, quality));
    }

    @Given("an item named {string} with sellIn {int} and quality {int} for {string}")
    public void anItemNamedWithSellInAndQualityWithDescription(String name, int sellIn, int quality) {
        this.targetInventory = List.of(new Item(name, sellIn, quality));
    }

    @When("the inventory system updates the item aging for {int} day(s)")
    public void theInventorySystemUpdatesTheItemAgingForDay(int days) {
        for (int i = 0; i < days; i++) {
            agingService.ageInventory(targetInventory);
        }
    }

    @Then("the item should have sellIn {int} and quality {int}")
    public void theItemShouldHaveSellInAndQuality(int expectedSellIn, int expectedQuality) {
        Item agedResult = targetInventory.get(0);

        assertThat(agedResult.sellIn)
                .as("Verifying timeline track for: " + agedResult.name)
                .isEqualTo(expectedSellIn);

        assertThat(agedResult.quality)
                .as("Verifying quality clamping strategy for: " + agedResult.name)
                .isEqualTo(expectedQuality);
    }
}
