SRCDATE = "20140418"

KV = "3.8.7"

SRC_URI[md5sum] = "84a7f8f23baa3ba044eb9e315a5a2082"
SRC_URI[sha256sum] = "b93842dfe9224d5603e50002c56e755c7a3d98184a608d13a922dc7d983848e9"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

