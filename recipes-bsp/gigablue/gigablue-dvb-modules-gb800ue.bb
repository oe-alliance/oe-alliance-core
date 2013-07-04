SRCDATE = "20130624"

KV = "3.1.1"

SRC_URI[md5sum] = "31a2183251c8e84a1fb797ea12575290"
SRC_URI[sha256sum] = "b66f5e96f01a1b92b4cc72569167ccb60ae2636f11b5408626da1af5e9b826c1"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

