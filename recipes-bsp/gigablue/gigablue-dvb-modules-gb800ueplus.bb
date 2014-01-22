SRCDATE = "20140120"

KV = "3.8.7"

SRC_URI[md5sum] = "39cc69e4ed3ce531c95035adb7b340a1"
SRC_URI[sha256sum] = "2c11fea8ee51d667deec08d571d77430d6f44dbf9ea3634ebf70e7e7ca5bb896"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

