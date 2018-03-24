package com.awaragi.poc.userstorage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FederatedUserModel {
  private final String username;
  private final String password;
  private boolean isEnabled = true;
  private String email = "test@test.com";
  private boolean emailVerified = true;
  private String firstName = "test first name";
  private String lastName = "test last name";

  private Map<String, List<String>> attributes = new HashMap<>();
  private List<String> roles;

  public FederatedUserModel(String username, String password) {
    this.username = username;
    this.password = password;

    // Put some ramdom attributes
    attributes.put("An attribute", Arrays.asList("test1", "test2", "test3"));
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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

  public String getEmail() {
    return email;
  }
}
