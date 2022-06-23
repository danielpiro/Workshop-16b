package com.example.demo.Database.DTOobjects.GlobalServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "id_generator")
public class IdGeneratorDTO {
    @Id
    private String idPrefix;
    private long suffix;

}
