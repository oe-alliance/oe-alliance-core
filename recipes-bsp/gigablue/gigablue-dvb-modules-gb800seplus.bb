SRCDATE = "20130831"

KV = "3.8.3"

SRC_URI[md5sum] = "e98c3211ebe04c1216d92428afcd32d4"
SRC_URI[sha256sum] = "251ce076241910f9dc69266b5bae0eae2851b8f8b32e1fe45b30079a52d4300d"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-gb800xxplus-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

