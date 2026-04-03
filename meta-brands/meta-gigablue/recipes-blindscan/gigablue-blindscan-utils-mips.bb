SRCDATE = "20151013"

PACKAGES = "gigablue-blindscan-dvbs-utils-mips gigablue-blindscan-dvbc-utils-mips"

SRC_URI[md5sum] = "c51ad24b8fcd74671bf9790707485c7b"
SRC_URI[sha256sum] = "5d77489dd0c588bb962a588e2b919cf018d5d7e732ff8a1e08db1d4a16236252"

SRC_URI = "https://source.mynonpublic.com/gigablue/blindscan/gigablue-blindscan-utils-${SRCDATE}.tgz"

FILES:gigablue-blindscan-dvbc-utils-mips = "${bindir}/tda1002x"
FILES:gigablue-blindscan-dvbs-utils-mips = "${bindir}/gigablue_blindscan"

require gigablue-blindscan-utils.inc
