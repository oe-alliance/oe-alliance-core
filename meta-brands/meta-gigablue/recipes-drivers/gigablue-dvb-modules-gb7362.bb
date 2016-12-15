SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "e0d85057990b9cae9378dd06caabe046"
SRC_URI[sha256sum] = "e07f2f8c8301746d1d16f3c7c2101318d21f0e9b946986ce4e5d2815494b9e50"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}a.zip"

require gigablue-dvb-modules.inc
