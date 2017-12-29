SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
SRCNAME = "mutagen"

DEPENDS = "python"
RDEPENDS_${PN} = "python-shell"

SRC_URI = "https://github.com/quodlibet/mutagen/archive/release-${PV}.tar.gz"

SRC_URI[md5sum] = "32ba118e6cd78b670182139d05e89023"
SRC_URI[sha256sum] = "693a2d619df4b422a8571d4917a9f3e3eaabea98be8fb6d75dec758707b2e79e"

S = "${WORKDIR}/mutagen-release-${PV}"

inherit distutils

include python-package-split.inc

