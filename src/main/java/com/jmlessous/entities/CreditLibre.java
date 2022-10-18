package com.jmlessous.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "CREDIT_LIBRE")

public class CreditLibre extends Credit implements Serializable {

    private Float tauxInteret;

}
