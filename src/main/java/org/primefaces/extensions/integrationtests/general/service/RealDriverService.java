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
public class RealDriverService {

    @Getter
    private List<Driver> drivers;

    @PostConstruct
    public void init() {
        drivers = new ArrayList<>();
        drivers.add(new Driver(1, "Lewis"));
        drivers.add(new Driver(2, "Max"));
        drivers.add(new Driver(3, "Charles"));
        drivers.add(new Driver(4, "Lando"));
    }
}
