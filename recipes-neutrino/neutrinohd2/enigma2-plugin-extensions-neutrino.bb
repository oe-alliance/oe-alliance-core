DESCRIPTION = "neutrinoHD2"
MAINTAINER = "mohousch based on open-nhd2-r64.12 from scp"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://AUTHORS;md5=03e8a220d5624784d6f4b1f4e5ded717"

MODULE = "nhd2-exp"
NEUTRINOHDBIN = "neutrino"

SUBURI = "svn/branches;module=nhd2-exp"

SRCREV = "AUTOINC"

URL = "http://www.cuberevo-forum.eu"

require neutrinohd-extra.inc

SRC_URI += "\
	file://neutrinohd2-extra.tar.gz \
	"

dirs755 += "\
	/usr/lib/enigma2/python/Plugins/Extensions/NeutrinoHD2 \
	"

FILES_${PN} += "\
	/usr/lib/enigma2/python/Plugins/Extensions/NeutrinoHD2 \
	"

DEPENDS += " \
	tuxtxt-enigma2 \
	"

RDEPENDS_${PN} += " \
	tuxtxt-enigma2 \
	"

