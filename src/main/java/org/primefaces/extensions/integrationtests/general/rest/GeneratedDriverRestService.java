package org.primefaces.extensions.integrationtests.general.rest;

import org.primefaces.extensions.integrationtests.general.model.Driver;
import org.primefaces.extensions.integrationtests.general.service.GeneratedDriverService;
import org.primefaces.extensions.integrationtests.general.service.RealDriverService;
import org.primefaces.model.rest.AutoCompleteSuggestion;
import org.primefaces.model.rest.AutoCompleteSuggestionResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/generateddriver")
public class GeneratedDriverRestService {

    @Inject
    private GeneratedDriverService service;

    @GET
    @Path("/autocomplete")
    @Produces({ MediaType.APPLICATION_JSON })
    public AutoCompleteSuggestionResponse autocomplete(@QueryParam("query") String query) {
        String queryLowerCase = query.toLowerCase();
        List<Driver> allDrivers = service.getDrivers();
        return new AutoCompleteSuggestionResponse(allDrivers.stream()
                .filter(d -> d.getName().toLowerCase().contains(queryLowerCase))
                .map(d -> new AutoCompleteSuggestion(Integer.toString(d.getId()), d.getName()))
                .collect(Collectors.toList()));
    }
}
