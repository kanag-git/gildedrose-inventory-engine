package com.gildedrose.endpoints;

import com.gildedrose.service.GildedRoseInventoryAgingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GildedRoseInventoryEndpoint {
    private final GildedRoseInventoryAgingService gildedRoseInventoryAgingService;

    public GildedRoseInventoryEndpoint(final GildedRoseInventoryAgingService gildedRoseInventoryAgingService) {
        this.gildedRoseInventoryAgingService = gildedRoseInventoryAgingService;
    }
}
