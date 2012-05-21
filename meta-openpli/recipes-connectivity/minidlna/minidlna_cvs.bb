SUMMARY = "lightweight DLNA/UPnP-AV server targeted at embedded systems"
HOMEPAGE = "http://sourceforge.net/projects/minidlna/"
SECTION = "network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b1a795ac1a06805cf8fd74920bc46b5c"
DEPENDS = "libexif jpeg libid3tag flac libvorbis sqlite3 libav util-linux"
SRCDATE = "20120408"
PV = "1.0.99+cvs${SRCDATE}"
PR = "r0"

SRC_URI = "cvs://anonymous@minidlna.cvs.sourceforge.net/cvsroot/minidlna;module=minidlna \
           file://default_sqlite_caches.diff"

S = "${WORKDIR}/${PN}"

inherit autotools gettext

PACKAGES =+ "${PN}-utils"

FILES_${PN}-utils = "${bindir}/test*"
