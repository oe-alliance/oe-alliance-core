SUMMARY = "A very fast JSON encoder/decoder for Python modified for e2iplayer"
HOMEPAGE = "https://github.com/e2iplayer/python-cjson"
LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0816bf71e8b244e6de5618a54522e845"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.2.2+git"
PKGV = "1.2.2+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/python-cjson.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools3_legacy

DISTUTILS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/e2icjson \
    "

# Remove "egg-info" files. If datadir or site-packages dir is empty, remove it.
setuptools3_do_install:append() {
    rm -f ${D}${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/e2icjson/*.egg-info
    rmdir -p --ignore-fail-on-non-empty ${D}${datadir} ${D}/${PYTHON_SITEPACKAGES_DIR} || true
}

include ${PYTHON_PN}-package-split.inc
