FROM jboss/keycloak:3.4.3.Final

MAINTAINER Pierre Awaragi (pierre.awaragi@cgi.com)
LABEL version="1.0"
LABEL description="Packaged instance of KeyCloak with KBA and Custom user-federation"

RUN mkdir -p /opt/jboss/keycloak/providers
COPY deployables/user-federation-1.0-SNAPSHOT.jar /opt/jboss/keycloak/providers
COPY deployables/kb-authentication-1.0-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments
COPY deployables/themes/ /opt/jboss/keycloak/themes/
