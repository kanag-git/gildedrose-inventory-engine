package com.gildedrose.database.enities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Item")
@Table(name = "ITEM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SELL_IN", nullable = false)
    private int sellIn;

    @Column(name = "QUALITY", nullable = false, precision = 19, scale = 4)
    private BigDecimal quality;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @Column(name = "CREATION_TIME", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;
}
