SUMMARY = "CLI for extracting streams from various websites to a video player of your choosing"
DESCRIPTION = "Streamlink is a command-line utility that pipes video streams from various services into a video player, such as VLC."
HOMEPAGE = "https://github.com/streamlink/streamlink"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "${@bb.utils.contains("PYTHON_PN", "python", "file://LICENSE;md5=7c0be52291b7252b878da806d185b1d1", "file://LICENSE;md5=7c0be52291b7252b878da806d185b1d1", d)}"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "setuptools", "setuptools3", d)} ${PYTHON_PN}-dir gitpkgv

RDEPENDS_${PN} = "${PYTHON_PN}-core \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-backports-shutil-get-terminal-size ${PYTHON_PN}-backports-shutil-which", "", d)} \
    ${PYTHON_PN}-ctypes \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-futures", "${PYTHON_PN}-futures3", d)} \
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
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} \
    ${PYTHON_PN}-websocket-client \
    "

SRCREV = "${AUTOREV}"
PV = "${@bb.utils.contains("PYTHON_PN", "python", "1.27.2.1+git${SRCPV}", "2.1.1+git${SRCPV}", d)}"
PKGV = "${@bb.utils.contains("PYTHON_PN", "python", "1.27.2.1+git${GITPKGV}", "2.1.1+git${GITPKGV}", d)}"

SRC_URI = "${@bb.utils.contains("PYTHON_PN", "python", "git://github.com/Billy2011/streamlink-27;protocol=https", "git://github.com/streamlink/streamlink.git;protocol=https", d)}"

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
