SRCDATE = "20150425"

KV = "3.14.2"

SRC_URI[md5sum] = "cb28fbf968070b0edd79a1105251df5c"
SRC_URI[sha256sum] = "98bdbcbf53eb803505ce6fa6a752bd44bfa49fb096a089b08019cc9f4e2d6094"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
