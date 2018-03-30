package com.awaragi.poc.userstorage;

import java.util.Optional;

public interface UserService {
  FederatedUserModel getUserDetails(String username);
}
