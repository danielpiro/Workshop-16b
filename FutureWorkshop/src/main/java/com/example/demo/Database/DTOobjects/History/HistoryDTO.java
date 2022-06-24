package com.example.demo.Database.DTOobjects.History;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "history")
public class HistoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseID;

    private String userID;
    private String storeID;
    private String itemName;
    private float itemPrice;
    private int itemAmount;
    private LocalDateTime timeOfTransaction;


    public HistoryDTO(String userID, String storeID, String itemName, float itemPrice, int itemAmount, LocalDateTime timeOfTransaction) {
        this.userID = userID;
        this.storeID = storeID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
        this.timeOfTransaction = timeOfTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDTO that = (HistoryDTO) o;
        return Float.compare(that.itemPrice, itemPrice) == 0 &&
                itemAmount == that.itemAmount &&
                Objects.equals(purchaseID, that.purchaseID) &&
                Objects.equals(userID, that.userID) &&
                Objects.equals(storeID, that.storeID) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(timeOfTransaction, that.timeOfTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseID, userID, storeID, itemName, itemPrice, itemAmount);
    }
}
