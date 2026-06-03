package com.gildedrose.model;

public class AgingItem {
    private final Item item;

    public AgingItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.item = item;
    }

    public String getName() {
        return item.name;
    }

    public boolean isExpired() {
        return item.sellIn < 0;
    }

    public boolean hasQuality() {
        return item.quality > 0;
    }

    public void passOneDay() {
        item.sellIn--;
    }

    public void degradeQualityBy(int value) {
        item.quality -= value;
    }

    public void improveQualityBy(int value) {
        item.quality += value;
    }

    public int getDaysRemaining(){
        return item.sellIn;
    }

    public void dropQualityToMin() {
        item.quality = 0;
    }

    public ItemCategory getCategory(){
        return ItemCategory.fromItemName(item.name);
    }

    public void clampQualityBounds(int lowerBound, int upperBound) {
        item.quality = Math.max(item.quality, lowerBound);
        item.quality = Math.min(item.quality, upperBound);
    }
}
