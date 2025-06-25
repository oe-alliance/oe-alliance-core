SUMMARY = "E2iPlayer"
DESCRIPTION = "Watch Videos Online"
HOMEPAGE = "https://gitlab.com/iptvplayer-for-e2/"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

SRC_URI = "git://github.com/oe-mirrors/e2iplayer.git;protocol=http;branch=python3;protocol=https"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "git${GITPKGV}"
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
