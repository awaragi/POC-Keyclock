package com.awaragi.storage;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.List;
import java.util.Map;

public class SimpleUserStorageProvider implements UserLookupProvider, UserStorageProvider, CredentialInputValidator {
  private static final Logger LOG = Logger.getLogger(SimpleUserStorageProvider.class);
  private final KeycloakSession session;
  private final ComponentModel model;

  private UserService userService;
  private static final String SUPPORTED_CREDENTIAL_TYPE = "password";
  private static final String SECRET_QUESTION = "SECRET_QUESTION";

  public SimpleUserStorageProvider(KeycloakSession session, ComponentModel model, UserService userService) {
    this.session = session;
    this.model = model;
    this.userService = userService;
  }

  @Override
  public UserModel getUserById(String s, RealmModel realmModel) {
    LOG.warn("getUserById not supported.");
    throw new UnsupportedOperationException();
  }

  @Override
  public UserModel getUserByUsername(String username, RealmModel realmModel) {
    LOG.infof("Get by username: %s", username);

    try {
      return this.createUserModel(realmModel, username);
    } catch (NotFoundException ex) {
      LOG.errorf("Federated user not found: %s", username);
      return null;
    }
  }

  @Override
  public UserModel getUserByEmail(String email, RealmModel realmModel) {
    LOG.infof("Get by email: %s", email);

    try {
      return this.createUserModel(realmModel, email);
    } catch (NotFoundException ex) {
      LOG.error("Federated user (by email) not found: " + email);
      return null;
    }
  }

  private UserModel createUserModel(RealmModel realm, String rawUsername) throws NotFoundException {

    String username = rawUsername.toLowerCase().trim();

    FederatedUserModel remoteUser = userService.getUserDetails(username);

    if (remoteUser == null) {
      throw new NotFoundException();
    }

    LOG.infof("Creating user model for: %s", username);
    UserModel userModel = session.userLocalStorage().addUser(realm, username);

    // for now the user is linked to source until it is correctly authenticated
    userModel.setFederationLink(model.getId());

    userModel.setEnabled(remoteUser.isEnabled());
    userModel.setEmail(remoteUser.getEmail());
    userModel.setEmailVerified(remoteUser.isEmailVerified());
    userModel.setFirstName(remoteUser.getFirstName());
    userModel.setLastName(remoteUser.getLastName());

    if (remoteUser.getAttributes() != null) {
      Map<String, List<String>> attributes = remoteUser.getAttributes();
      for (String attributeName : attributes.keySet()) {
        userModel.setAttribute(attributeName, attributes.get(attributeName));
      }
    }

    if (remoteUser.getRoles() != null) {
      for (String role : remoteUser.getRoles()) {
        RoleModel roleModel = realm.getRole(role);
        if (roleModel != null) {
          userModel.grantRole(roleModel);
          LOG.infof("Granted user %s, role %s", username, role);
        }
      }
    }

    return userModel;
  }

  @Override
  public void close() {
    LOG.info("Closing Simple User Storage");
  }

  @Override
  public boolean supportsCredentialType(String credentialType) {
    return SUPPORTED_CREDENTIAL_TYPE.equals(credentialType);
  }

  @Override
  public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String credentialType) {
    return credentialType.equals(CredentialModel.PASSWORD)
        && userService.getUserDetails(userModel.getUsername()) != null;
  }

  @Override
  public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
    LOG.infof("Validating user %s", userModel.getUsername());

    if (!supportsCredentialType(credentialInput.getType()) || !(credentialInput instanceof UserCredentialModel)) {
      return false;
    }

    UserCredentialModel cred = (UserCredentialModel) credentialInput;
    FederatedUserModel federatedUserModel = userService.getUserDetails(userModel.getUsername());
    if (federatedUserModel == null) {
      return false;
    }

    boolean validCredentials = federatedUserModel.getPassword().equals(cred.getValue());

    if (validCredentials) {
      LOG.infof("Completing import of valid user by detaching/unlink it from its source: %s", federatedUserModel);
      userModel.setFederationLink(null);

      // update credentials using clear password
      session.userCredentialManager().updateCredential(realmModel, userModel, credentialInput);

      // secret question
      UserCredentialModel secretQuestionCredential = UserCredentialModel.secret(federatedUserModel.getSecretAnswer());
      secretQuestionCredential.setType(SECRET_QUESTION);
      session.userCredentialManager().updateCredential(realmModel, userModel, secretQuestionCredential);
    }

    return validCredentials;
  }
}
