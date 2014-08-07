SRCDATE = "20140807"

KV = "3.14.2"

SRC_URI[md5sum] = "ae6d0033aeb8c6b06834d85df58fd65d"
SRC_URI[sha256sum] = "f3f8ed4ad6ca35d53480291f9ccd7ceb4b3dce8116004fed7bf90bb5b0714565"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-gbquad-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

