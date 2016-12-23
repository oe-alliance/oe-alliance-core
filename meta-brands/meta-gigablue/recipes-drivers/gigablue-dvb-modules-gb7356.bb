SRCDATE = "20161219"

KV = "4.0.1"

SRC_URI[md5sum] = "57c763a73440e0d51f2b46c883127b40"
SRC_URI[sha256sum] = "507e4ecd306ff1f04129a9129fffa88eb03e69bcae142acea91d690abd3d344e"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
