SUMMARY = "DVB / MPEG stream analyzer"
AUTHOR = "Rainer Scherg <rasc@users.sourceforge.net>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SRCDATE = "20081001"
PV = "0.0+cvs${SRCDATE}"
PR = "r1"

SRC_URI = "git://git.code.sf.net/p/tuxbox-cvs/apps.git;protocol=git;module=apps/dvb/dvbsnoop"

S = "${WORKDIR}/git/dvb/dvbsnoop"

inherit autotools
