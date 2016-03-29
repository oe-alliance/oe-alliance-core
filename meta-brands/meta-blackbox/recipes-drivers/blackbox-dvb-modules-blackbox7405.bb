KV = "3.14.21"
SRCDATE = "20160329"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "eeb3159647414a2f936b2ac0cf0d0dc9"
SRC_URI[sha256sum] = "092f2d6fdfdadefca8bb09db6071d5c3d900002f5e6a938bf9b3a976a6ff2540"

SRC_URI = "http://source.mynonpublic.com/unibox/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
