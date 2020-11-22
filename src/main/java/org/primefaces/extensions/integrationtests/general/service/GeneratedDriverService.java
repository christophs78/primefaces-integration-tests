package org.primefaces.extensions.integrationtests.general.service;

import lombok.Getter;
import org.primefaces.extensions.integrationtests.general.model.Driver;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class GeneratedDriverService {

    @Getter
    private List<Driver> drivers;

    @PostConstruct
    public void init() {
        drivers = new ArrayList<>();
        for (int i=0; i<50; i++) {
            drivers.add(new Driver(i, "Driver No. " + i));
        }
    }
}
