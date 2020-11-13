package be.chesteric31;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("be.chesteric31");

        noClasses()
            .that()
                .resideInAnyPackage("be.chesteric31.service..")
            .or()
                .resideInAnyPackage("be.chesteric31.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..be.chesteric31.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
