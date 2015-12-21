KV = "3.18.24"
SRCDATE = "20151221"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "8a1fbfe5f2743611925e6c4db2ba0bb4"
SRC_URI[sha256sum] = "5023ba70ac494c926ce1068a9b129fb994f001ccb207b90f77cee596c31d6187"
