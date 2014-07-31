SRCDATE = "20140731"

KV = "3.12.1"

SRC_URI[md5sum] = "ebec726a0968fe661eca019207e4d59d"
SRC_URI[sha256sum] = "6b96c9f46198a5be98ca8f2d6ae9a8ecbe464beee09bb4b3a901d232909abba7"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

