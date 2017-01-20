SRCDATE = "20170120"

KV = "3.14.28-1.12"

SRC_URI[md5sum] = "189c708c6e52b8d185047cb244b7cf6e"
SRC_URI[sha256sum] = "405b54452721a319adc5b83e3d13065a4ae1a882f7693cf770ea292c0ca1a04f"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
