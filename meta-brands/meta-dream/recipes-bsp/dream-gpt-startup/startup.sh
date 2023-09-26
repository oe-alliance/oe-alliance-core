#!/bin/sh
### BEGIN INIT INFO
# Provides:           startup
# Required-Start:     $local_fs
# Required-Stop:      $local_fs
# Default-Start:      S
# Default-Stop:
# Short-Description:  dreambox gpt multiboot files
### END INIT INFO

echo -n "start dream-data.sh"
/etc/init.d/dream-data.sh &
: exit 0