SRCDATE = "20140205"

KV = "3.8.7"

SRC_URI[md5sum] = "1b2404b05b664c6c662c9444c5919727"
SRC_URI[sha256sum] = "b33498a64da824e7c1890f3fe233b4adb877864360ddb7eb56a3f7ffec5fa4ce"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

