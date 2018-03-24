# Keycloak POC

This is a POC on how to add a custom federated user storage provider to Keycloak.

That includes:
 * Fake federated source of users from a properties files (users.properties).
 * Validate password from federated source
 * Update the Keycloak UserModel with data from the federated source (attributes, email, etc...)
 * Update the credential and detach that user from the federated source

To build the provider jar:
        mvn clean install
That will copy the provider package to src/etc/providers

Now build and start:
        cd src/etc/
        docker-compose up

Go to localhost:8080 and in User Federation add simple-user-storage then login.

        username: pierre
        password: pierre

References:
https://github.com/keycloak/keycloak/tree/master/examples/providers/user-storage-simple
https://github.com/Smartling/keycloak-user-migration-provider/blob/master/user-migration-federation-provider

