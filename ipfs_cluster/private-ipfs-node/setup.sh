#!/bin/sh
set -e

user=ipfs
repo="$IPFS_PATH"
configRepo="/etc/config"

if [ `id -u` -eq 0 ]; then
  echo "Changing user to $user"
  # ensure folder is writable
  su-exec "$user" test -w "$repo" || chown -R -- "$user" "$repo"
  # restart script with new privileges
  exec su-exec "$user" "$0" "$@"
fi

# 2nd invocation with regular user
ipfs version

echo "/key/swarm/psk/1.0.0/" > $repo/swarm.key 
echo "/base16/" >> $repo/swarm.key
echo "cd7bff5978e45beebf6ce3482ed2617da49053e735746a7b9ce053fde5815bc5" >> $repo/swarm.key


if [ -e "$repo/config" ]; then
  echo "Found IPFS fs-repo at $repo"
else
  echo 'Initializing IPFS...'
  ipfs init
  ipfs config Addresses.API /ip4/0.0.0.0/tcp/5001
  ipfs config Addresses.Gateway /ip4/0.0.0.0/tcp/8080
  echo 'Removing default bootstrap nodes...'
  ipfs bootstrap rm --all
fi

if [ -e "$configRepo/webui.car" ];
then
  echo "WEBUI has been detected, starting to import it ..."
  ipfs dag import $configRepo/webui.car
fi

if [ ! -z $SWARM_PEER ]; then ipfs bootstrap add $SWARM_PEER; fi

exec ipfs daemon "$@"