#!/bin/bash
# Ask the user for their name
echo port -- press enter to keep default
read customPort
echo type -- press enter to keep defaul
read customType
echo org -- press enter to keep default
read customOrg



if [ -z "$customPort" ] && [ -z "$customOrg" ] && [ -z "$customType" ] ; then
    echo "no variables set"
    exit
else
  if [ -f "env.list" ]; then
    rm env.list
  fi
  touch env.list
  if [ -n "$customPort" ] ; then echo "port=$customPort" >> env.list
  fi
  if [ -n "$customType" ] ; then echo "type=$customType" >> env.list
  fi
  if [ -n "$customOrg" ] ; then echo "org=$customOrg" >> env.list
  fi
  cat env.list
fi

