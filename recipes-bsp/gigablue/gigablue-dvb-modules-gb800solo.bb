SRCDATE = "20130810"

KV = "3.1.1"

SRC_URI[md5sum] = "4ed18c38831234028390486b1cf9c323"
SRC_URI[sha256sum] = "945848a84ce08234a4f0c046c8df7bddc98c043d0f3171d9cac0ab094256cdd0"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

