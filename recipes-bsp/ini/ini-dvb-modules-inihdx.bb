KV = "3.6.0"
SRCDATE = "20130530"

SRC_URI[md5sum] = "49f3ae4f0e4926c5d5a592f2f9c0ac28"
SRC_URI[sha256sum] = "c714903620ba106dae39f5158c1018b5533f927ad217ff961296cbf6eca48f9a"

SRC_URI = "http://127.0.0.1/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"
#SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
