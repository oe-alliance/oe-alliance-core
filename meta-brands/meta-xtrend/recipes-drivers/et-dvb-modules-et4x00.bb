KV = "4.10.6"
SRCDATE = "20170413"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "3db7497610be9bf52bd8bc81a9a87cc7"
SRC_URI[sha256sum] = "55a7abfabfef7f9ca7d7b91f156a3cd7735d9703ad12f6aa8a37c9b0fd97d506"
