#!/usr/bin/env bash

mkdir -p /opt/jboss/keycloak/providers
cp -u /shared/user-federation-1.0-SNAPSHOT.jar /opt/jboss/keycloak/providers
cp -u /shared/kb-authentication-1.0-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments

cp -r /shared/themes/* /opt/jboss/keycloak/themes/

/opt/jboss/docker-entrypoint.sh -b 0.0.0.0 --debug