SUMMARY = "CLI for extracting streams from various websites to a video player of your choosing"
DESCRIPTION = "Streamlink is a command-line utility that pipes video streams from various services into a video player, such as VLC."
HOMEPAGE = "https://github.com/streamlink/streamlink"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7c0be52291b7252b878da806d185b1d1"

inherit setuptools3 ${PYTHON_PN}-dir gitpkgv

RDEPENDS_${PN} = "${PYTHON_PN}-core \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-futures3 \
    ${PYTHON_PN}-isodate \
    ${PYTHON_PN}-iso3166 \
    ${PYTHON_PN}-iso639 \
    ${PYTHON_PN}-misc \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pycryptodome \
    ${PYTHON_PN}-pysocks \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-singledispatch \
    ${PYTHON_PN}-websocket-client \
    "

SRCREV = "${AUTOREV}"
PV = "2.1.1+git${SRCPV}"
PKGV = "2.1.1+git${GITPKGV}"

SRC_URI = "git://github.com/streamlink/streamlink.git;protocol=https"

S = "${WORKDIR}/git"

do_install_append() {
    rm -rf ${D}${bindir}
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/streamlink_cli
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/streamlink-*.egg-info/*
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/streamlink/plugins/.removed
}

PACKAGES = "${PN}"

FILES_${PN} = " \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*/*.py \
    "
