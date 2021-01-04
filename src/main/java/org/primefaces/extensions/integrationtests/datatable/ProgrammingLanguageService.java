package org.primefaces.extensions.integrationtests.datatable;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ProgrammingLanguageService {

    public List<ProgrammingLanguage> getLangs() {
        List<ProgrammingLanguage> progLanguages = new ArrayList<>();
        progLanguages.add(new ProgrammingLanguage(1, "Java", 1995, ProgrammingLanguage.ProgrammingLanguageType.COMPILED));
        progLanguages.add(new ProgrammingLanguage(2, "C#", 2000, ProgrammingLanguage.ProgrammingLanguageType.COMPILED));
        progLanguages.add(new ProgrammingLanguage(3, "JavaScript", 1995, ProgrammingLanguage.ProgrammingLanguageType.INTERPRETED));
        progLanguages.add(new ProgrammingLanguage(4, "TypeScript", 2012, ProgrammingLanguage.ProgrammingLanguageType.INTERPRETED));
        progLanguages.add(new ProgrammingLanguage(5, "Python", 1990, ProgrammingLanguage.ProgrammingLanguageType.INTERPRETED));

        return progLanguages;
    }

    public ProgrammingLanguage create(Integer id, String language) {
        return new ProgrammingLanguage(id, language, 1987, ProgrammingLanguage.ProgrammingLanguageType.INTERPRETED);
    }
}
