SRCDATE = "20141002"

KV = "3.14.2"

SRC_URI[md5sum] = "bbab35d241b1516c07f5b97ff9ada99d"
SRC_URI[sha256sum] = "4e61806c9924dd5f94ae2456eeda29c5e317b39fc8856766b88e44726436be8e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

