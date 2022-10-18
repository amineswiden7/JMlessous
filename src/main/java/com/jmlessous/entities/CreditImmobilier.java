package com.jmlessous.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "CREDIT_IMMOBILIER")

public class CreditImmobilier  extends Credit implements Serializable {

    private Float tauxNominal;
    private String type;
}
