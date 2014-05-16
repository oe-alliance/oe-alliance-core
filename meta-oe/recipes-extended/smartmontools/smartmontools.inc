SECTION = "console/utils"
SUMMARY = "Control and monitor storage systems using S.M.A.R.T."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
INC_PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/smartmontools/smartmontools-${PV}.tar.gz"
S = "${WORKDIR}/smartmontools-${PV}"

inherit autotools
