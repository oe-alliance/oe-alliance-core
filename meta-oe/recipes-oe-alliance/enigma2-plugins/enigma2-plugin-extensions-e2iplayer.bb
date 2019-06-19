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

inherit distutils-openplugins gettext

DEPENDS = "gettext-native python"
RRECOMMENDS_${PN} = " \
        enigma2-plugin-extensions-e2iplayer-deps \
        python-compression \
        python-core \
        python-html \
        python-e2icjson \
        python-json \
        python-shell \
        python-subprocess \
        python-textutils \
        "

RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
        ${libdir}/enigma2/python/Plugins/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*-py2.7.egg-info/* \
        ${libdir}/enigma2/python/Plugins/*/locale/*/LC_MESSAGES/*.po \
        "

deltask package_qa
