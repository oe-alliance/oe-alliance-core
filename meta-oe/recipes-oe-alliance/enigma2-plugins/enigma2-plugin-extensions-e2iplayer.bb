SUMMARY = "E2iPlayer"
DESCRIPTION = "Watch Videos Online"
HOMEPAGE = "https://gitlab.com/iptvplayer-for-e2/"
SECTION = "multimedia"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

SRC_URI = "git://gitlab.com/e2i/e2iplayer.git;protocol=http"
SRC_URI_append += "file://ffmpeg4.patch"

S = "${WORKDIR}/git"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r2"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)} gettext

DEPENDS = "gettext-native python"
RRECOMMENDS_${PN} = " \
        enigma2-plugin-extensions-e2iplayer-deps \
        ${PYTHON_PN}-compression \
        ${PYTHON_PN}-core \
        ${PYTHON_PN}-html \
        ${PYTHON_PN}-e2icjson \
        ${PYTHON_PN}-json \
        ${PYTHON_PN}-shell \
        ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} \
        ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-textutils", "", d)} \
        "

deltask package_qa
