package org.entando.entandoupgrade;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.entando.entandoupgrade");

        noClasses()
            .that()
            .resideInAnyPackage("org.entando.entandoupgrade.service..")
            .or()
            .resideInAnyPackage("org.entando.entandoupgrade.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.entando.entandoupgrade.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
