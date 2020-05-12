KV = "4.10.6"
SRCDATE = "20200402"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "408302a4bc0d65bd07f47c112c79f36d"
SRC_URI[sha256sum] = "00890af6d8ae91ac7200e232409ba058af87e6c0446e05cddddf7334baae3026"
