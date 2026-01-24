SUMMARY = "CLI for extracting streams from various websites to a video player of your choosing"
DESCRIPTION = "Streamlink is a command-line utility that pipes video streams from various services into a video player, such as VLC."
HOMEPAGE = "https://github.com/streamlink/streamlink"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca97af75b78809a5c401f63ead0f59f2"

DEPENDS += "python3-versioningit-native"

inherit python3-dir setuptools3 gittag

RDEPENDS:${PN} = "python3-core \
    python3-ctypes \
    python3-exceptiongroup \
    python3-futures3 \
    python3-isodate \
    python3-pycountry \
    python3-lxml \
    python3-misc \
    python3-pkgutil \
    python3-pycryptodome \
    python3-pysocks \
    python3-requests \
    python3-shell \
    python3-singledispatch \
    python3-websocket-client \
    python3-trio \
    python3-certifi \
    python3-urllib3 \
    python3-trio-websocket \
    "

PV = "git"
PKGV = "${GITPKGVTAG}"

SRCREV_streamlink = "${AUTOREV}"
SRCREV_plugins = "${AUTOREV}"

SRCREV_FORMAT = "streamlink_plugins"

SRC_URI = "git://github.com/streamlink/streamlink.git;protocol=https;name=streamlink;branch=master \
           git://github.com/oe-mirrors/streamlink-plugins;protocol=https;name=plugins;destsuffix=additional-plugins;branch=master"

do_unpack:append() {
    bb.build.exec_func('do_prepare_plugins_dir', d)
}

do_prepare_plugins_dir() {
    cp -f ${UNPACKDIR}/additional-plugins/*.py ${S}/src/streamlink/plugins
}

do_install:append() {
    rm -rf ${D}${bindir}
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/streamlink_cli
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/*.egg-info
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/streamlink/plugins/.removed
    rm -rf ${D}${datadir}
}

include python3-package-split.inc

PACKAGES = "${PN}"

FILES:${PN} += " \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*.pyc \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*/*.pyc \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*/*/*.pyc \
    "

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink-*.egg-info/* \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/plugins/.removed \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/streamlink/*/*/*.py \
    "
