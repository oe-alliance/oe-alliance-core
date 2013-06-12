KV = "3.3.8-2.0"
SRCDATE = "20130514"

SRC_URI[md5sum] = "1caab667d70063150f1effc6919fb98d"
SRC_URI[sha256sum] = "a514e950bdf0a09a3ce63e761ff12fb6d455f4055c1b432fa93549ce7afa9d2c"

SRC_URI = "http://127.0.0.1/software/drivers/ini-x000-drivers-3.6.0-${SRCDATE}.zip"
#SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
