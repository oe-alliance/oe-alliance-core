SRCDATE = "20150425"

KV = "3.14.2"

SRC_URI[md5sum] = "98dedcefc8ba7e5a0101a0074ba7cc99"
SRC_URI[sha256sum] = "9bc2da849568107db3436f709ca87e1382c7942b118a26e055ec1710d27dfeb9"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
