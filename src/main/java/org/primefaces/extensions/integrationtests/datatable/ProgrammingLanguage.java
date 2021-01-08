package org.primefaces.extensions.integrationtests.datatable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguage implements Serializable {
    private static final long serialVersionUID = 398626647627541586L;
    private Integer id;
    private String name;
    private Integer firstAppeared;
    private ProgrammingLanguageType type;

    enum ProgrammingLanguageType {
        COMPILED,
        INTERPRETED
    }
}
