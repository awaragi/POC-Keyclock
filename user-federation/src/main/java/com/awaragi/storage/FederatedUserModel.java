package com.awaragi.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FederatedUserModel {
  private final String username;
  private final String password;
  private boolean isEnabled = true;
  private boolean emailVerified = true;
  private String secretQuestion = "";
  private Map<String, List<String>> attributes = new HashMap<>();
  private List<String> roles;

  public FederatedUserModel(String username, String password, String secretQuestion) {
    this.username = username;
    this.password = password;
    this.secretQuestion = secretQuestion;
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

  public String getSecretQuestion() { return secretQuestion; }

  public String getEmail() {
    return username + "@" + username + ".com";
  }
}
