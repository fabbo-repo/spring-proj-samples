package com.prueba.homeworkapp.commons.containers;

import dasniko.testcontainers.keycloak.ExtendableKeycloakContainer;

public class MockKeycloakContainer extends ExtendableKeycloakContainer<MockKeycloakContainer> {

    private static final String IMAGE_VERSION = "keycloak/keycloak:21.0";

    private static MockKeycloakContainer container;

    private MockKeycloakContainer() {
        super(IMAGE_VERSION);
    }

    public static MockKeycloakContainer getInstance() {
        if (container == null) {
            container = new MockKeycloakContainer()
                    .withRealmImportFile(
                            "keycloak/realm-export.json"
                    );
        }
        return container;
    }
}
