SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "f915b2d3a3952d582af042c5125c0025"
SRC_URI[sha256sum] = "c8a00903257e08eed88ed111413026d6e0ca6b866779a41c1d95fc506571bbda"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}a.zip"

require gigablue-dvb-modules.inc
