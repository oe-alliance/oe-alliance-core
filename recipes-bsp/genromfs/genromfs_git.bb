require genromfs.inc

inherit native

PROVIDES = "genromfs-native"

LICENSE="CLOSED"
SRCREV = "e4225b49a7be0ae9d39e98f2175dd674c0d6b1ea"
PV = "0.0+git${SRCREV}"
PR = "${INC_PR}.0"

SRC_URI = "git://github.com/chexum/genromfs;protocol=git;branch=master"
S = "${WORKDIR}/git"

BBCLASSEXTEND = "native nativesdk"
