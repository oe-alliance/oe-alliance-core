KV = "4.10.6"
SRCDATE = "20200402"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "cb9152f01cf817a66ba8e45078e6d900"
SRC_URI[sha256sum] = "670948b277e2a446cc784f4d60d033e3e3a7fa02b4131dcdffc3862d4f0a8b51"
