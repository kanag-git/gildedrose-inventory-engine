package com.gildedrose.service;

import com.gildedrose.agingpolicy.AgedBrieAgingPolicy;
import com.gildedrose.agingpolicy.BackstagePassesAgingPolicy;
import com.gildedrose.agingpolicy.ItemAgingPolicyRegistry;
import com.gildedrose.agingpolicy.ItemAgingPolicySettings;
import com.gildedrose.agingpolicy.StandardItemAgingPolicy;
import com.gildedrose.agingpolicy.SulfurasItemAgingPolicy;
import com.gildedrose.model.Item;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class GildedRoseInventoryAgingPortTest {

    private GildedRoseInventoryAgingService gildedRoseInventoryAgingPort;

    @BeforeEach
    void setUp() {
        val standardPolicySettings = new ItemAgingPolicySettings.StandardPolicySettings(1, 2, 0, 50);
        val agedBriePolicySettings = new ItemAgingPolicySettings.AgedBriePolicySettings(1, 2, 0, 50);
        val backstagePassPolicySettings = new ItemAgingPolicySettings.BackstagePassPolicySettings(1, 10, 5, 0, 50);

        val testPolicies = List.of(
                new StandardItemAgingPolicy(standardPolicySettings),
                new AgedBrieAgingPolicy(agedBriePolicySettings),
                new BackstagePassesAgingPolicy(backstagePassPolicySettings),
                new SulfurasItemAgingPolicy());

        final ItemAgingPolicyRegistry itemAgingPolicyRegistry = new ItemAgingPolicyRegistry(testPolicies);

        this.gildedRoseInventoryAgingPort = new GildedRoseInventoryServiceImpl(itemAgingPolicyRegistry);
    }

    @Nested
    @DisplayName("Global or common items test suite")
    class GlobalItemRuleTestSuite {
        @Test
        @DisplayName("When sellIn day passes any item, Then quality should never drop below zero")
        void qualityNeverDropsBelowZeroForAnyItems() {
            //Given
            final var anyItems = List.of(new Item("Burger", 0, 0),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0));

            //When
            gildedRoseInventoryAgingPort.ageInventory(anyItems);

            //Then
            assertThat(anyItems.get(0).quality).isEqualTo(0);
            assertThat(anyItems.get(1).quality).isEqualTo(0);
        }

        @Test
        @DisplayName("When sellIn day passes any item except Sulfuras, Then quality must never exceed max threshold of 50")
        void qualityNeverExceedAboveFiftyForStandardItemsExceptSulfuras() {
            //Given
            final var standardItems = List.of(new Item("Aged Brie", 20, 50),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 3, 48),
                    new Item("Sulfuras, Hand of Ragnaros", 10, 80));

            //When
            gildedRoseInventoryAgingPort.ageInventory(standardItems);

            //Then
            assertThat(standardItems.get(0).quality).isEqualTo(50);
            assertThat(standardItems.get(1).quality).isEqualTo(50);
            assertThat(standardItems.get(2).quality).isEqualTo(80);
        }
    }

    @Nested
    @DisplayName("DefaultStandard or normal Items test suite")
    class DefaultStandardItemTestSuite {

        @Test
        @DisplayName("When sellIn day passes and quality is greater than zero, Then quality decreases by 1")
        void qualityGreaterThanZeroDecreasesByOneWhenDayPasses() {
            //Given
            final var defaultStandardItem = List.of(new Item("+5 Dexterity Vest", 10, 20));

            //When
            gildedRoseInventoryAgingPort.ageInventory(defaultStandardItem);

            //Then
            assertThat(defaultStandardItem.get(0).quality).isEqualTo(19);
            assertThat(defaultStandardItem.get(0).sellIn).isEqualTo(9);
        }

        @Test
        @DisplayName("When expired, Then quality decreases by two")
        void qualityDecreasedByTwoWhenExpired() {
            //Given
            final var defaultStandardItems = List.of(new Item("+5 Dexterity Vest", 0, 20),
                    new Item("Sulfuras, Hand of Ragnaros", -1, 80));

            //When
            gildedRoseInventoryAgingPort.ageInventory(defaultStandardItems);

            //Then
            assertThat(defaultStandardItems.get(0).quality).isEqualTo(18);
            assertThat(defaultStandardItems.get(0).sellIn).isEqualTo(-1);

            assertThat(defaultStandardItems.get(1).quality).isEqualTo(80);
            assertThat(defaultStandardItems.get(1).sellIn).isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("Aged Brie Items test suite")
    class AgedBrieTestSuite {

        @Test
        @DisplayName("When sellIn day passes, Then quality increases by 1")
        void qualityIncreasesWhenOlder() {
            //Given
            final var agedBrie = List.of(new Item("Aged Brie", 10, 20));

            //When
            gildedRoseInventoryAgingPort.ageInventory(agedBrie);

            //Then
            assertThat(agedBrie.get(0).quality).isEqualTo(21);
            assertThat(agedBrie.get(0).sellIn).isEqualTo(9);
        }

        @Test
        @DisplayName("When expired, Then quality increases by 2")
        void qualityIncreasesWhenExpired() {
            //Given
            final var agedBrieItems =  List.of(new Item("Aged Brie", -1, 20),
                    new Item("Aged Brie", -1, 49));

            //When
            gildedRoseInventoryAgingPort.ageInventory(agedBrieItems);

            //Then
            assertThat(agedBrieItems.get(0).quality).isEqualTo(22);
            assertThat(agedBrieItems.get(0).sellIn).isEqualTo(-2);

            assertThat(agedBrieItems.get(1).quality).isEqualTo(50);
            assertThat(agedBrieItems.get(1).sellIn).isEqualTo(-2);
        }
    }

    @Nested
    @DisplayName("Sulfuras Items test suite")
    class SulfurasItemTestSuite {

        @Test
        @DisplayName("When sellIn day passes, never decrease quality or sellIn day")
        void qualityOrSoldNeverDecrease() {
            //Given
            final var sulfuras = List.of(new Item("Sulfuras, Hand of Ragnaros", 10, 80));

            //When
            gildedRoseInventoryAgingPort.ageInventory(sulfuras);

            //Then
            assertThat(sulfuras.get(0).quality).isEqualTo(80);
            assertThat(sulfuras.get(0).sellIn).isEqualTo(10);
        }
    }

    @Nested
    @DisplayName("Backstage concert items test suite")
    class BackstageConcertItemTestSuite {

        @Test
        @DisplayName("When sellIn day six to ten days range, Then quality increases by 2")
        void qualityIncreasesByTwoWhenSixToTenDaysRange() {
            //Given
            final var backstageItems = List.of(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 7, 15));

            //When
            gildedRoseInventoryAgingPort.ageInventory(backstageItems);

            //Then
            assertThat(backstageItems.get(0).quality).isEqualTo(22);
            assertThat(backstageItems.get(0).sellIn).isEqualTo(9);

            assertThat(backstageItems.get(1).quality).isEqualTo(17);
            assertThat(backstageItems.get(1).sellIn).isEqualTo(6);
        }

        @Test
        @DisplayName("When quality is fifty, Then no quality change")
        void noQualityChangeWhenQualityIsFifty() {
            //Given
            final var backstageItems = List.of(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 49)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 3, 50));

            //When
            gildedRoseInventoryAgingPort.ageInventory(backstageItems);

            //Then
            assertThat(backstageItems.get(0).quality).isEqualTo(50);
            assertThat(backstageItems.get(0).sellIn).isEqualTo(8);

            assertThat(backstageItems.get(1).quality).isEqualTo(50);
            assertThat(backstageItems.get(1).sellIn).isEqualTo(2);
        }

        @Test
        @DisplayName("When sellIn day one to five days range, Then quality increases by 3")
        void qualityIncreasesByThreeWhenFiveToOneDayRange() {
            //Given
            final var backstageItems =List.of(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 1, 15));

            //When
            gildedRoseInventoryAgingPort.ageInventory(backstageItems);

            //Then
            assertThat(backstageItems.get(0).quality).isEqualTo(23);
            assertThat(backstageItems.get(0).sellIn).isEqualTo(4);

            assertThat(backstageItems.get(1).quality).isEqualTo(18);
            assertThat(backstageItems.get(1).sellIn).isEqualTo(0);
        }

        @Test
        @DisplayName("When sellIn day more than ten days, Then quality increases by 1")
        void qualityIncreasesByOneWhenMoreThanTenDays() {
            //Given
            final var backstageItems = List.of(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 15, 15));

            //When
            gildedRoseInventoryAgingPort.ageInventory(backstageItems);

            //Then
            assertThat(backstageItems.get(0).quality).isEqualTo(21);
            assertThat(backstageItems.get(0).sellIn).isEqualTo(10);

            assertThat(backstageItems.get(1).quality).isEqualTo(16);
            assertThat(backstageItems.get(1).sellIn).isEqualTo(14);
        }

    }
}