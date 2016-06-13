KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160610"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "c55a2ec7a5f17d800f59dec3d368f44e"
SRC_URI[sha256sum] = "b7bf80a03f5d81d4bead1c77733e6d5872eab98754a5e421d37aff486c853c0b"
