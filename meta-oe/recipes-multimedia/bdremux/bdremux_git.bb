SUMMARY = "bdremux - a blu-ray movie stream remuxer"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=374d7e305a3d05bc98bec47c241f02af"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/oe-alliance/bdremux.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
