SRCDATE = "20141231"

KV = "3.14.2"

SRC_URI[md5sum] = "9a66858bb50ad7957298a65070c735a5"
SRC_URI[sha256sum] = "6c488d7081ed28ea3544d6a144a619f32d40d65f1be0b72c143f10b4d4bb51d8"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
