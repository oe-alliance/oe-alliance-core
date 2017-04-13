KV = "4.10.6"
SRCDATE = "20170413"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "4b6dac7f1e385a13be1d1b7147345f76"
SRC_URI[sha256sum] = "cb6d1e63827268ba09817be24a4f575549c4a3fb1773c25598b3a996cb02b4c8"
