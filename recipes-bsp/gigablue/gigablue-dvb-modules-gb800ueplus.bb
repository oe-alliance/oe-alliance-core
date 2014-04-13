SRCDATE = "20140413"

KV = "3.8.7"

SRC_URI[md5sum] = "f87aac3acfa724d071c49c044d85f379"
SRC_URI[sha256sum] = "faf80ea25bb6d469a44c12be221a14fc6a9f98bfb3c8b26c170b6990bcf33887"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

