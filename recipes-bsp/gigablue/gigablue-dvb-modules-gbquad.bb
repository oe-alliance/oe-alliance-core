SRCDATE = "20121128"

KV = "2.6.37-2.8"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "d704006ed30e97c81ba8ddd338d7e6a3"
SRC_URI[sha256sum] = "d18997e7b7152e28462e5c5b374abcbc0c4e5a670a007534e919e190611f2dc1"

require gigablue-dvb-modules.inc

