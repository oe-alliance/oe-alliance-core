FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://util-DVBC_ANNEX_AC.patch"

SRCREV = "3d43b280298c"
CFLAGS += "-fPIC"
LDFLAGS += "-static"