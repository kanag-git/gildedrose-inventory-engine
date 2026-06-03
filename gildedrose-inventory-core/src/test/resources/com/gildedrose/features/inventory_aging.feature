Feature: Gilded Rose Inventory Management
  As the Gilded Rose Innkeeper,
  I want our shop's inventory data to automatically upgrade or degrade and update daily
  So that we don't sell spoiled items, overcharge for legendary items. [cite: 1]

  # ===========================================================================
  # SECTION 1: GLOBAL CONSTRAINTS (Common policy for all the items)
  # ===========================================================================

  Scenario: Items cannot be broken or have negative quality
    Given an item named "Cheap standard item" with sellIn 5 and quality 0
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn 4 and quality 0

  Scenario: High-quality items are capped to protect market prices
    Given an item named "Aged Brie" with sellIn 10 and quality 50
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn 9 and quality 50

  # ===========================================================================
  # SECTION 2: NORMAL / STANDARD ITEMS
  # ===========================================================================

  Scenario: Normal shop items degrade steadily over time
    Given an item named "+5 Dexterity Vest" with sellIn 10 and quality 20
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn 9 and quality 19

  Scenario: Normal shop items degrade twice as fast once they spoil
    Given an item named "+5 Dexterity Vest" with sellIn 0 and quality 20
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn -1 and quality 18

  # ===========================================================================
  # SECTION 3: THE SPECIALS ITEMS
  # ===========================================================================

  Scenario Outline: Aged Brie actually improves the older it gets
    Given an item named "Aged Brie" with sellIn <initialSellIn> and quality <initialQuality>
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn <expectedSellIn> and quality <expectedQuality>

    Examples:
      | Case                               | initialSellIn | initialQuality | expectedSellIn | expectedQuality |
      | Improves by 1 while fresh          | 10            | 20             | 9              | 21              |
      | Improves by 2 once past expiry     | -1            | 20             | -2             | 22              |

  Scenario Outline: Legendary artifacts like Sulfuras defy time and aging laws
    Given an item named "Sulfuras, Hand of Ragnaros" with sellIn <initialSellIn> and quality 80
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn <expectedSellIn> and quality 80

    Examples:
      | Case                         | initialSellIn | expectedSellIn | [cite: 17, 18]
      | Freshly stocked item         | 10            | 10             | [cite: 19, 20]
      | Historically expired         | -1            | -1             | [cite: 21, 22, 23]

  # ===========================================================================
  # SECTION 4: CONCERT TICKETS
  # ===========================================================================

  Scenario Outline: Backstage pass values spike as the concert date approaches
    Given an item named "Backstage passes to a TAFKAL80ETC concert" with sellIn <initialSellIn> and quality <initialQuality>
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn <expectedSellIn> and quality <expectedQuality>

    Examples:
      | Case                          | initialSellIn | initialQuality | expectedSellIn | expectedQuality |
      | More than 10 days             | 11            | 20             | 10             | 21              |
      | 10 days left                  | 10            | 20             | 9              | 22              |
      | 5 days left                   | 5             | 20             | 4              | 23              |
      | Concert Day                   | 1             | 15             | 0              | 18              |
      | After concert                 | 0             | 20             | -1             | 0               |

  # ===========================================================================
  # SECTION 5: CONJURED ITEMS
  # ===========================================================================

  Scenario Outline: Conjured items are unstable and degrade twice as fast
    Given an item named "Conjured Mana Cake" with sellIn <initialSellIn> and quality 20
    When the inventory system updates the item aging for 1 day
    Then the item should have sellIn <expectedSellIn> and quality <expectedQuality>

    Examples:
      | Case               | initialSellIn | expectedSellIn | expectedQuality |
      | Unexpired          | 10            | 9              | 18              |
      | Expired            | 0             | -1             | 16              |