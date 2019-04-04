SUMMARY = "A very fast JSON encoder/decoder for Python modified for e2iplayer"
HOMEPAGE = "https://github.com/e2iplayer/python-cjson"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0816bf71e8b244e6de5618a54522e845"

SRC_URI = "git://github.com/e2iplayer/python-cjson.git;protocol=http"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit distutils gitpkgv

PV = "1.2.1+git${SRCPV}"
PKGV = "1.2.1+git${GITPKGV}"

DISTUTILS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/e2icjson \
    "

# Remove "egg-info" files. If datadir or site-packages dir is empty, remove it.
distutils_do_install_append() {
	rm -f ${D}${libdir}/enigma2/python/Plugins/Extensions/IPTVPlayer/libs/e2icjson/*.egg-info
	rmdir -p --ignore-fail-on-non-empty ${D}${datadir} ${D}/${PYTHON_SITEPACKAGES_DIR} || true
}
