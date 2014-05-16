SRCDATE = "20130823"

KV = "3.1.1"

SRC_URI[md5sum] = "d1991ba5c82330e904e7757da3308dd1"
SRC_URI[sha256sum] = "8132fd629865942a133cf6d51693cb561b602838d160cb091ea4581a5d528303"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

