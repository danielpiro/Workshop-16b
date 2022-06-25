package com.example.demo.Database.DTOobjects.Store;

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
@Table(name = "polices")
public class PolicyDTO {
    @Id
    private String policyId;
    private String policyType;
    private long predicateId;
    private String ForeignKeyPolicyId1;
    private String ForeignKeyPolicyId2;

    public PolicyDTO(String policyId, String policyType, long predicateId) {
        this.policyId = policyId;
        this.policyType = policyType;
        this.predicateId = predicateId;
    }

    public PolicyDTO(String policyId, String policyType, String foreignKeyPolicyId1, String foreignKeyPolicyId2) {
        this.policyId = policyId;
        this.policyType = policyType;
        ForeignKeyPolicyId1 = foreignKeyPolicyId1;
        ForeignKeyPolicyId2 = foreignKeyPolicyId2;
    }
}
