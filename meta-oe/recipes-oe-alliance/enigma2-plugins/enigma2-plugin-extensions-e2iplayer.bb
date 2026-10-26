SUMMARY = "E2iPlayer"
DESCRIPTION = "Watch videos and live streams online - the oe-mirrors E2iPlayer, based on E2iPlayer by samsamsam"
HOMEPAGE = "https://github.com/oe-mirrors/e2iplayer"
MAINTAINER = "openATV team"
SECTION = "multimedia"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"
require conf/python/python3-compileall.inc

SRC_URI = "git://github.com/oe-mirrors/e2iplayer.git;protocol=https;branch=python3"

inherit gittag
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r0"

inherit setuptools3-openplugins gettext

DEPENDS += "gettext-native python3-future-native python3"
RRECOMMENDS:${PN} = " \
        enigma2-plugin-extensions-e2iplayer-deps \
        python3-compression \
        python3-core \
        python3-html \
        python3-json \
        python3-shell \
        python3-websocket-client \
        "

RDEPENDS:${PN}-src = "${PN}"
FILES:${PN}-src = " \
        ${libdir}/enigma2/python/Plugins/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*/*/*/*/*/*.py \
        ${libdir}/enigma2/python/Plugins/*-py2.7.egg-info/* \
        ${libdir}/enigma2/python/Plugins/*/locale/*/LC_MESSAGES/*.po \
        "
deltask package_qa
