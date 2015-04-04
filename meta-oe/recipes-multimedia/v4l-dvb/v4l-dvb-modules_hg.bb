PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20100904"
KV = "2.6.18-7.4-dm800"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "9b3f0075a840f525c4f83351b7e5ee47"
SRC_URI[sha256sum] = "facdbf5a57c172325a7aadb168c69b90773a0612f5f574677ab0409df76a9d03"

SRC_URI = "http://source.mynonpublic.com/v4l-dvb-modules_${KV}-${SRCDATE}.zip"

S = "${WORKDIR}/"

require v4l-dvb-modules.inc