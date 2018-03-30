# Keycloak POC

This is a POC on how to add a custom federated user storage provider to Keycloak.

Links to test:
 * [Simple login with redirect to app](http://localhost:8080/auth/realms/WADA/protocol/openid-connect/auth?client_id=wada-oidc-client&scope=oidc&response_type=token)
 * [Forced login with redirect to app](http://localhost:8080/auth/realms/WADA/protocol/openid-connect/auth?client_id=wada-oidc-client&scope=oidc&response_type=token&prompt=login)
 * [Manage my account](http://localhost:8080/auth/realms/WADA/account)

That includes:
 * Fake federated source of users from a properties files (users.properties).
 * Validate password from federated source
 * Update the Keycloak UserModel with data from the federated source (attributes, email, etc...)
 * Update the credential and detach that user from the federated source

To build the provider jar:
        mvn clean package
That will compile, package and deploy the provider package to instance/providers

Now build and start:
        cd instance
        docker-compose up

- Go to localhost:8080 
- Access the admin console (admin/admin)
- Navigate to User Federation section
- Add new federated 
- Add simple-user-storage then login.

        username: pierre
        password: pierre

References:
https://github.com/keycloak/keycloak/tree/master/examples/providers/user-storage-simple
https://github.com/Smartling/keycloak-user-migration-provider/blob/master/user-migration-federation-provider

