KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160701"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "3e7876fd1ec2a95db2de59321a4dc510"
SRC_URI[sha256sum] = "4f6660f7e3cd12c156ec7f07ec8e56c23647711be543092eb5fb43e62f72b2b9"
