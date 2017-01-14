SRCDATE = "20170114"

KV = "3.14.28-1.12"

SRC_URI[md5sum] = "4793b27824c253a619c2de33db685775"
SRC_URI[sha256sum] = "8be7c302765c3e482e0bd176cb8073f054981b2e242227cd0369251647634eed"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7252-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
