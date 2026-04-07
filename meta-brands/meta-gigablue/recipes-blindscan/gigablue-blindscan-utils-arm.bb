SRCDATE = "20171115a"

RREPLACES:gigablue-blindscan-dvbc-utils-arm = "gigablue-blindscan-dvbc-utils-${MACHINE}"
RCONFLICTS:gigablue-blindscan-dvbs-utils-arm = "gigablue-blindscan-dvbs-utils-${MACHINE}"

PACKAGES = "gigablue-blindscan-dvbs-utils-arm gigablue-blindscan-dvbc-utils-arm"

SRC_URI[md5sum] = "ba5843ab5a6ac0fa634370ad1663c0be"
SRC_URI[sha256sum] = "805889447809196d91fb657466945e3535938ffb228cea40dde8ed2fcca7eb57"

SRC_URI = "https://source.mynonpublic.com/gigablue/blindscan/gigablue-blindscan-utils-arm-${SRCDATE}.zip"

FILES:gigablue-blindscan-dvbc-utils-arm = "${bindir}/tda1002x"
FILES:gigablue-blindscan-dvbs-utils-arm = "${bindir}/gigablue_blindscan"

require gigablue-blindscan-utils.inc
