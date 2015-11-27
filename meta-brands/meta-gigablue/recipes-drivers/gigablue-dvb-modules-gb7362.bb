SRCDATE = "20151023"

KV = "3.14.2"

SRC_URI[md5sum] = "e93ee3b4908e564fe744ccd1c3d71fb6"
SRC_URI[sha256sum] = "b8ac60edf62dc21f13bcc3e1ba0b1f036e3e40a307b6006eb553787aa47a341e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
