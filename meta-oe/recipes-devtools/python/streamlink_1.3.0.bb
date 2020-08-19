SUMMARY = "CLI for extracting streams from various websites to a video player of your choosing"
DESCRIPTION = "Streamlink is a command-line utility that pipes video streams from various services into a video player, such as VLC."
HOMEPAGE = "https://github.com/streamlink/streamlink"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=67e73aa1a18a474a727af66319626ed4"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "setuptools", "setuptools3", d)} ${PYTHON_PN}-dir

RDEPENDS_${PN} = "${PYTHON_PN}-core \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-backports-shutil-which", "", d)} \
    ${PYTHON_PN}-ctypes \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-futures", "${PYTHON_PN}-futures3", d)} \
    ${PYTHON_PN}-iso3166 \
    ${PYTHON_PN}-iso639 \
    ${PYTHON_PN}-misc \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pycryptodome \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-singledispatch \
    ${@bb.utils.contains("PYTHON_PN", "python", "${PYTHON_PN}-subprocess", "", d)} \
    ${PYTHON_PN}-websocket-client \
    "

SRC_URI = "git://github.com/streamlink/streamlink.git;protocol=git"
SRCREV = "${PV}"
S = "${WORKDIR}/git"

do_install_append() {
    rm -rf ${D}${bindir}
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/streamlink_cli
}

PACKAGES = "${PN}"

FILES_${PN} = " \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*/*.py \
    "
