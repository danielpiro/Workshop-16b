package com.example.demo.Database.DTOobjects.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "Store")
public class StoreDTO {
    @Id
    private String storeId;

    private String storeName;

    private String storeState;

    private float storeRating;

}
