SRCDATE = "20130621"

KV = "3.3.6-1.2"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "e81a98aa20d803a0dc6b9c30c56733fb"
SRC_URI[sha256sum] = "6c541b772cdf9dde0bb895e5b735fb557e1a9339435f93596e94e0cd75e44bb8"

require gigablue-dvb-modules.inc

