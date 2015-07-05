SRCDATE = "20150608"

KV = "3.14.2"

SRC_URI[md5sum] = "8fddf0d7cf7009e13615d7255c9e776a"
SRC_URI[sha256sum] = "16b604794c26df26465bf05e3a5d0ca3527dba47b775812ded258e8ab8b2139d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
