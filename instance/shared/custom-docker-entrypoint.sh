#!/usr/bin/env bash

mkdir -p /opt/jboss/keycloak/providers
cp -u /shared/user-federation-1.0-SNAPSHOT.jar /opt/jboss/keycloak/providers
cp -u /shared/kb-authentication-1.0-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments
cp /shared/*.ftl /opt/jboss/keycloak/themes/base/login
cp /shared/login_messages_en.properties /opt/jboss/keycloak/themes/base/login/messages/messages_en.properties
cp /shared/login_messages_ar.properties /opt/jboss/keycloak/themes/base/login/messages/messages_ar.properties

/opt/jboss/docker-entrypoint.sh -b 0.0.0.0 --debug