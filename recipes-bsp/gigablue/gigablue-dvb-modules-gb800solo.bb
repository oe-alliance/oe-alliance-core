SRCDATE = "20120831"

KV = "3.1.1"

SRC_URI[md5sum] = "bf64ec3fb60c0a33b281077f762e9dec"
SRC_URI[sha256sum] = "a9adfb6cd1369cc07ec721da38ead72184c21f47055481472fa69f2b713113e7"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

