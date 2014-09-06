SRCDATE = "20140904"

KV = "3.14.2"

SRC_URI[md5sum] = "0a2e2b2d03e104238561792cdba04da6"
SRC_URI[sha256sum] = "c8d13f0ee65b7f995d05070184f4ef1ef373fd215a2d2030754234f82e4e05cc"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

