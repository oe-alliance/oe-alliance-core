#!/bin/sh

cd /usr/bin/hubFwUpdaterCLI

./hubFwUpdaterCLI version > ./curver.txt
read line < ./curver.txt
curver=${line#Firmware version:}
echo $curver > ./curver.txt

line=$(cat /usr/bin/hubFwUpdaterCLI/GLHubIsp.ini | grep "BinFile = ")
line=${line#BinFile = }
newver=${line:2:4}

case "$1" in
version)
  echo "gigablue-hubfwupdatercli - checking version..."
  if [ ${curver} != ${newver} ]; then
    echo "gigablue-hubfwupdatercli - Current:"${curver}" New:"${newver}
    echo "gigablue-hubfwupdatercli - Update is required!"
    exit 1
  else
    echo "gigablue-hubfwupdatercli - Current:"${curver}" New:"${newver}
    echo "gigablue-hubfwupdatercli - Update is not required!"
  fi
  ;;

isp)

  if [ ${curver} != ${newver} ]; then
    echo "gigablue-hubfwupdatercli - Updating..."
    ./hubFwUpdaterCLI isp
    echo "gigablue-hubfwupdatercli - Done."
  else
    echo "gigablue-hubfwupdatercli - Done."
  fi
  ;;

*)
  echo "Usage: hubFwUpdaterCLI {version|isp}"
  exit 0

esac

exit 0
