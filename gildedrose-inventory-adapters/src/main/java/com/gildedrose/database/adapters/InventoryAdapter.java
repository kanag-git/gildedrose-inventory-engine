package com.gildedrose.database.adapters;

import com.gildedrose.database.repositories.ItemRepository;
import com.gildedrose.model.Item;
import com.gildedrose.port.InventoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryAdapter implements InventoryRepositoryPort {
    private ItemRepository itemRepository;
    @Override
    public void saveAll(final List<Item> items) {
    }
}
