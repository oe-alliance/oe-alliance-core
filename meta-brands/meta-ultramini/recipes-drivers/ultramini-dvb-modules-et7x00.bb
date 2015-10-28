KV = "3.18.18"
SRCDATE = "20151026"

SRC_URI = "http://source.mynonpublic.com/ultramini/${MACHINE_DRIVER}-drivers-${KV}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "988d6589de8a6282258729b428eaa008"
SRC_URI[sha256sum] = "cd3512e45b651cf14efb9fae5322a67726f8a14e03d66ae1548b908b837ce9dc"
