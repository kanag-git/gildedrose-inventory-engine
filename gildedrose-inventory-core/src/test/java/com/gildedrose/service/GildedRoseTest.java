package com.gildedrose.service;

import com.gildedrose.model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class GildedRoseTest {

    @Nested
    @DisplayName("Global or common items test suite")
    class GlobalItemRuleTestSuite {
        @Test
        @DisplayName("When expired, Then quality should never drop below zero")
        void qualityNeverDropsBelowZeroForAnyItems() {
            //Given
            final var anyItems = new Item[]{new Item("Burger", 0, 0),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0)};
            final var gildedRose = new GildedRose(anyItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(anyItems[0].quality).isEqualTo(0);
            assertThat(anyItems[1].quality).isEqualTo(0);
        }

        @Test
        @DisplayName("When sellIn day passes any item except Sulfuras, Then quality must never exceed max threshold of 50")
        void qualityNeverExceedAboveFiftyForStandardItemsExceptSulfuras() {
            //Given
            final var standardItems = new Item[]{new Item("Aged Brie", 20, 50),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 3, 48),
                    new Item("Sulfuras, Hand of Ragnaros", 10, 80)};
            final var gildedRose = new GildedRose(standardItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(standardItems[0].quality).isEqualTo(50);
            assertThat(standardItems[1].quality).isEqualTo(50);
            assertThat(standardItems[2].quality).isEqualTo(80);
        }
    }

    @Nested
    @DisplayName("DefaultStandard or normal Items test suite")
    class DefaultStandardItemTestSuite {

        @Test
        @DisplayName("When sellIn day passes and quality is greater than zero, Then quality decreases by 1")
        void qualityGreaterThanZeroDecreasesByOneWhenDayPasses() {
            //Given
            final var defaultStandardItem = new Item("+5 Dexterity Vest", 10, 20);
            final var gildedRose = new GildedRose(new Item[]{defaultStandardItem});

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(defaultStandardItem.quality).isEqualTo(19);
            assertThat(defaultStandardItem.sellIn).isEqualTo(9);
        }

        @Test
        @DisplayName("When expired, Then quality decreases by two")
        void qualityDecreasedByTwoWhenExpired() {
            //Given
            final var defaultStandardItems = new Item[]{new Item("+5 Dexterity Vest", 0, 20),
                    new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
            final var gildedRose = new GildedRose(defaultStandardItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(defaultStandardItems[0].quality).isEqualTo(18);
            assertThat(defaultStandardItems[0].sellIn).isEqualTo(-1);

            assertThat(defaultStandardItems[1].quality).isEqualTo(80);
            assertThat(defaultStandardItems[1].sellIn).isEqualTo(-1);
        }
    }

    @Nested
    @DisplayName("Aged Brie Items test suite")
    class AgedBrieTestSuite {

        @Test
        @DisplayName("When sellIn day passes, Then quality increases by 1")
        void qualityIncreasesWhenOlder() {
            //Given
            final var agedBrie = new Item("Aged Brie", 10, 20);
            final var gildedRose = new GildedRose(new Item[]{agedBrie});

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(agedBrie.quality).isEqualTo(21);
            assertThat(agedBrie.sellIn).isEqualTo(9);
        }

        @Test
        @DisplayName("When expired, Then quality increases by 2")
        void qualityIncreasesWhenExpired() {
            //Given
            final var agedBrieItems = new Item[]{new Item("Aged Brie", -1, 20),
                    new Item("Aged Brie", -1, 49)};
            final var gildedRose = new GildedRose(agedBrieItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(agedBrieItems[0].quality).isEqualTo(22);
            assertThat(agedBrieItems[0].sellIn).isEqualTo(-2);

            assertThat(agedBrieItems[1].quality).isEqualTo(50);
            assertThat(agedBrieItems[1].sellIn).isEqualTo(-2);
        }
    }

    @Nested
    @DisplayName("Sulfuras Items test suite")
    class SulfurasItemTestSuite {

        @Test
        @DisplayName("When sellIn day passes, never decrease quality or sellIn day")
        void qualityOrSoldNeverDecrease() {
            //Given
            final var sulfuras = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
            final var gildedRose = new GildedRose(new Item[]{sulfuras});

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(sulfuras.quality).isEqualTo(80);
            assertThat(sulfuras.sellIn).isEqualTo(10);
        }
    }

    @Nested
    @DisplayName("Backstage concert items test suite")
    class BackstageConcertItemTestSuite {

        @Test
        @DisplayName("When sellIn day six to ten days range, Then quality increases by 2")
        void qualityIncreasesByTwoWhenSixToTenDaysRange() {
            //Given
            final var backstageItems = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 7, 15)};
            final var gildedRose = new GildedRose(backstageItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(backstageItems[0].quality).isEqualTo(22);
            assertThat(backstageItems[0].sellIn).isEqualTo(9);

            assertThat(backstageItems[1].quality).isEqualTo(17);
            assertThat(backstageItems[1].sellIn).isEqualTo(6);
        }

        @Test
        @DisplayName("When quality is fifty, Then no quality change")
        void noQualityChangeWhenQualityIsFifty() {
            //Given
            final var backstageItems = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 9, 49)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 3, 50)};
            final var gildedRose = new GildedRose(backstageItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(backstageItems[0].quality).isEqualTo(50);
            assertThat(backstageItems[0].sellIn).isEqualTo(8);

            assertThat(backstageItems[1].quality).isEqualTo(50);
            assertThat(backstageItems[1].sellIn).isEqualTo(2);
        }

        @Test
        @DisplayName("When sellIn day one to five days range, Then quality increases by 3")
        void qualityIncreasesByThreeWhenFiveToOneDayRange() {
            //Given
            final var backstageItems = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 1, 15)};
            final var gildedRose = new GildedRose(backstageItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(backstageItems[0].quality).isEqualTo(23);
            assertThat(backstageItems[0].sellIn).isEqualTo(4);

            assertThat(backstageItems[1].quality).isEqualTo(18);
            assertThat(backstageItems[1].sellIn).isEqualTo(0);
        }

        @Test
        @DisplayName("When sellIn day more than ten days, Then quality increases by 1")
        void qualityIncreasesByOneWhenMoreThanTenDays() {
            //Given
            final var backstageItems = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)
                    , new Item("Backstage passes to a TAFKAL80ETC concert", 15, 15)};
            final var gildedRose = new GildedRose(backstageItems);

            //When
            gildedRose.updateQuality();

            //Then
            assertThat(backstageItems[0].quality).isEqualTo(21);
            assertThat(backstageItems[0].sellIn).isEqualTo(10);

            assertThat(backstageItems[1].quality).isEqualTo(16);
            assertThat(backstageItems[1].sellIn).isEqualTo(14);
        }
    }
}