package org.primefaces.extensions.integrationtests.datatable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ProgrammingLanguageService {

    public List<ProgrammingLanguage> getLangs() {
        List<ProgrammingLanguage> progLanguages = new ArrayList<>();
        progLanguages.add(new ProgrammingLanguage(1, "Java", 1995));
        progLanguages.add(new ProgrammingLanguage(2, "C#", 2000));
        progLanguages.add(new ProgrammingLanguage(3, "JavaScript", 1995));
        progLanguages.add(new ProgrammingLanguage(4, "TypeScript", 2012));
        progLanguages.add(new ProgrammingLanguage(5, "Python", 1990));

        return progLanguages;
    }
}
