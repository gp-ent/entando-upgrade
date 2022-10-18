package org.entando.entandoupgrade;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.entando.entandoupgrade.EntandoUpgradeApp;
import org.entando.entandoupgrade.config.TestSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { EntandoUpgradeApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
