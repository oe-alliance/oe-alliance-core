DESCRIPTION = "Plugin for copy or move more files at once"
MAINTAINER = "ims"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9432c1f3d564948269193fd19a0ad0fd"
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/MovieManager.git;protocol=https;branch=master"

S="${WORKDIR}/git"

inherit setuptools3-openplugins
