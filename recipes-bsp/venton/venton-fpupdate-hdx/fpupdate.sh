#!/bin/sh


#
# so hacky.... it will be improved when driver will be improved and there will some more options
# sorry for that...

model=`cat /proc/stb/info/boxtype`

if [ $model == "ini-3000" ]; then
  file=/etc/fpupdate/RHS300_micom.bin
  if [ -e $file ]; then
    echo "Detected " $model
    echo "Upgrading Front Processor"
	/bin/fpupdate $file
    echo "Finshed!"
  fi
elif [ $model == "ini-5000" ]; then
  file=/etc/fpupdate/RHS500_micom.bin
  if [ -e $file ]; then
    echo "Detected " $model
    echo "Upgrading Front Processor"
    /bin/fpupdate $file
    echo "Finshed!"
  fi
elif [ $model == "ini-7000" ]; then
  file=/etc/fpupdate/RHS700_micom.bin
  if [ -e $file ]; then
    echo "Detected " $model
    echo "Upgrading Front Processor"
    /bin/fpupdate $file
    echo "Finshed!"
  fi
fi 


exit 0