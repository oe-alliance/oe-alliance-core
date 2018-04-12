KV = "4.10.6"
SRCDATE = "20180412"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d722139a289c629bf1573a114f94b98c"
SRC_URI[sha256sum] = "789683637220a0fb8d9736e9efe2c5bb5c90279981758b20ca50fbf9894cb712"
