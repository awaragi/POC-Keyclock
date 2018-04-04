package com.awaragi.storage;

import static java.util.Collections.singletonList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FederatedUserModel {
  private final String username;
  private final String password;
  private boolean isEnabled = true;
  private boolean emailVerified = true;
  private String secretAnswer = "";
  private Map<String, List<String>> attributes = new HashMap<>();
  private List<String> roles;

  public FederatedUserModel(String username, String password, String secretAnswer) {
    this.username = username;
    this.password = password;
    this.secretAnswer = secretAnswer;

  }

  private String camelCase(String s) {
    if(s == null) {
      return s;
    } else if(s.length() < 2) {
      return s.toUpperCase();
    } else {
      return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public String getFirstName() { return camelCase(username); }

  public String getLastName() {
    return "Van " + camelCase(username);
  }

  public Map<String, List<String>> getAttributes() {
    return attributes;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getPassword() {
    return password;
  }

  public String getSecretAnswer() { return secretAnswer; }

  public String getEmail() {
    return username + "@" + username + ".com";
  }

  @Override
  public String toString() {
    return "FederatedUserModel{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", isEnabled="
        + isEnabled + ", emailVerified=" + emailVerified + ", secretAnswer='" + secretAnswer + '\'' + ", attributes="
        + attributes + ", roles=" + roles + '}';
  }
}
