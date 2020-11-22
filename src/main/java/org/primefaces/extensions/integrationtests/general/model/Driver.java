package org.primefaces.extensions.integrationtests.general.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Driver implements Serializable {
    private static final long serialVersionUID = 8371414176714192877L;
    private int id;
    private String name;
}
