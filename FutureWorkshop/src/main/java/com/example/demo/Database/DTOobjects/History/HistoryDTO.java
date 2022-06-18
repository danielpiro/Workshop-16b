package com.example.demo.Database.DTOobjects.History;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


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



}
