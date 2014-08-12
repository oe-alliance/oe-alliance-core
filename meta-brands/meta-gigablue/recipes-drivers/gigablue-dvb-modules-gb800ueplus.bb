SRCDATE = "20140812"

KV = "3.14.2"

SRC_URI[md5sum] = "ddbe2d33332128aea9fd5f1fbf2ba12a"
SRC_URI[sha256sum] = "bb90856fb25eed88cc2a77b14046c0b91258725f828385cc7e0e29c452e6a311"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

