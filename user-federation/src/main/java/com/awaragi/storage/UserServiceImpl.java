package com.awaragi.storage;

import java.util.Properties;

public class UserServiceImpl implements UserService {
  protected Properties properties;

  /**
   * Constructor using pre-loaded properties
   */
  public UserServiceImpl(Properties properties) {
    this.properties = properties;
  }

  @Override
  public FederatedUserModel getUserDetails(String username) {
    FederatedUserModel federatedUserModel = null;
    if (properties.getProperty(username + ".password") != null) {
      federatedUserModel = new FederatedUserModel(username, properties.getProperty(username + ".password"), properties.getProperty(username + ".kba") );
    }
    return federatedUserModel;
  }
}
