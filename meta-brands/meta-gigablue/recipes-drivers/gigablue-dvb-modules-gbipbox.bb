SRCDATE = "20150326"

KV = "3.14.2"

SRC_URI[md5sum] = "9de5a5da95b262e78a9274459a282698"
SRC_URI[sha256sum] = "120a0fc86b0a6d38adb87b2618e9c0c232f57a6fe5e5d83329b834d7513b8cca"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
