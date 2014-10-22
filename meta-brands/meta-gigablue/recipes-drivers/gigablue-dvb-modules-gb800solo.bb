SRCDATE = "20141022"

KV = "3.9.6"

SRC_URI[md5sum] = "c5f997a6a03dbb04d9223b16d63a717e"
SRC_URI[sha256sum] = "417fc0230b5550a2847a8b324cc8bf72f0c87b64f90fe122e13fe93845691fb6"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xx-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
