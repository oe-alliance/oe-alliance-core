SRCDATE = "20151021"

KV = "3.9.6"

SRC_URI[md5sum] = "a3b12d6d34d42ba21a62fd9279813bf7"
SRC_URI[sha256sum] = "421d72858ff76826c2230c38f17c4ca49ceb09b71784ca17534a11594de2c1c5"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7325-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
