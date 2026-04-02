SRCDATE = "20151013"

SRC_URI[md5sum] = "c51ad24b8fcd74671bf9790707485c7b"
SRC_URI[sha256sum] = "5d77489dd0c588bb962a588e2b919cf018d5d7e732ff8a1e08db1d4a16236252"

SRC_URI = "https://source.mynonpublic.com/gigablue/blindscan/gigablue-blindscan-utils-${SRCDATE}.tgz"

PACKAGES = "gigablue-blindscan-dvbs-utils-mips gigablue-blindscan-dvbc-utils-mips"

require gigablue-blindscan-utils.inc

FILES:gigablue-blindscan-dvbc-utils-mips = "${bindir}/tda1002x"
FILES:gigablue-blindscan-dvbs-utils-mips = "${bindir}/gigablue_blindscan"
