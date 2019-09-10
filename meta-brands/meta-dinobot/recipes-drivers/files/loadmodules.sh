#!/bin/sh

echo "dinobot start!!!"
cd /lib/modules/4.4.35/extra/
./load

if [ -e /proc/stb/info/upd_path ]; then
  upd_path=`cat /etc/image-version |grep imagedir= | cut -d= -f2`
  if [ ! -z $upd_path ]; then
    echo "/$upd_path" > /proc/stb/info/upd_path
  fi
fi
