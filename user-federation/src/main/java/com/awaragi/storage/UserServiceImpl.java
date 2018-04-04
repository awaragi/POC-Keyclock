package com.awaragi.storage;

import static java.util.Collections.singletonList;

import java.util.Properties;

public class UserServiceImpl implements UserService {
  private static final String OTP = "otp";

  private Properties properties;

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

      // Disable OTP by config
      federatedUserModel.getAttributes().put(OTP, singletonList(properties.getProperty(username + ".otp")));

    }
    return federatedUserModel;
  }
}
