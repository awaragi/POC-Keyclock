package com.awaragi.storage;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimpleUserStorageProviderFactory implements UserStorageProviderFactory<SimpleUserStorageProvider> {
  private static final Logger LOG = Logger.getLogger(SimpleUserStorageProvider.class);

  public static final String PROVIDER_NAME = "simple-user-storage";

  protected Properties properties = new Properties();

  @Override
  public SimpleUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
    return new SimpleUserStorageProvider(keycloakSession, componentModel, new UserServiceImpl(properties));
  }

  @Override
  public String getId() {
    return PROVIDER_NAME;
  }

  @Override
  public void init(Config.Scope config) {
    InputStream is = getClass().getClassLoader().getResourceAsStream("users.properties");

    if (is == null) {
      LOG.warn("Could not find users.properties in classpath");
    } else {
      try {
        properties.load(is);
      } catch (IOException ex) {
        LOG.error("Failed to load users.properties file", ex);
      }
    }
  }
}
