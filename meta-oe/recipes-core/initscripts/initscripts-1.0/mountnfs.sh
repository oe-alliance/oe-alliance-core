#!/bin/sh
echo "Waiting for network, then mounting remote filesystems..."

have_cmd() { command -v "$1" >/dev/null 2>&1; }

net_is_up() {
  if have_cmd ip; then
    ip -4 addr show scope global 2>/dev/null | grep -q 'inet ' || return 1
    ip route show default 2>/dev/null | grep -q 'default' || return 1
    return 0
  fi
  if have_cmd ifconfig; then
    ifconfig 2>/dev/null | grep -q 'inet ' || return 1
  else
    return 1
  fi
  if have_cmd route; then
    route -n 2>/dev/null | grep -q '^0\.0\.0\.0' || return 1
  fi
  return 0
}

i=0
while [ "$i" -lt 30 ]; do
  if net_is_up; then
    mount -a
    exit 0
  fi
  i=$((i+1))
  sleep 1
done

echo "Network not ready after 30s, skipping mount -a."
exit 0