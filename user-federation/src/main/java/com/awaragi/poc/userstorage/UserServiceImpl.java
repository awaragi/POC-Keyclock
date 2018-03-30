package com.awaragi.poc.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class UserServiceImpl implements UserService {
  protected Properties properties;
  // map of loaded users in this transaction
  protected Map<String, UserModel> loadedUsers = new HashMap<>();
  public UserServiceImpl(Properties properties) {
    this.properties = properties;
  }

  @Override
  public FederatedUserModel getUserDetails(String username) {
    FederatedUserModel federatedUserModel = null;
    if (properties.getProperty(username) != null) {
      federatedUserModel = new FederatedUserModel(username, properties.getProperty(username) );
    }
    return federatedUserModel;
  }
}
