KV = "3.14.21"
SRCDATE = "20150217"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "d464b4362eeba47f517af55df42a4a70"
SRC_URI[sha256sum] = "3ac20875844a1fbb3a5051e871934fdabb7fd7fe425443701968714852bf933d"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
