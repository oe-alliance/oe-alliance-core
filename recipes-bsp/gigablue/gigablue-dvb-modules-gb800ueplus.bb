SRCDATE = "20131105"

KV = "3.8.7"

SRC_URI[md5sum] = "1650a2ac8790cc8afeb7eeb5e5b1f3ab"
SRC_URI[sha256sum] = "819010e3a2d0d21e082d7b8539f67c7b137bf0913551db0ddfa858ceb00836cf"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

