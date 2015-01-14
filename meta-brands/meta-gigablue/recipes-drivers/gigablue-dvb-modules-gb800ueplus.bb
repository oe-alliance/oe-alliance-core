SRCDATE = "20150114"

KV = "3.14.2"

SRC_URI[md5sum] = "2a5a3418514c5adb4db893b215431b93"
SRC_URI[sha256sum] = "f03eff2df694ddf087eb0617c70949c3f5cdfc61707e799e9da7db5fef442d24"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
