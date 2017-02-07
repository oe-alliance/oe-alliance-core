#!/bin/sh
if [ $1 == "on" ]
  then
    # [Standby] leave standby, do something if STB is waking up from standby
    echo "[Standby] STB is ON" > /tmp/Standby.info
  else
    # [Standby] enter standby, do something if STB is entering standby
    echo "[Standby] STB is OFF" > /tmp/Standby.info
fi
