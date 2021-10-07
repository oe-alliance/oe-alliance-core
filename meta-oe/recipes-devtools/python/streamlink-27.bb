SUMMARY = "Streamlink[-27] is a fork of the Streamlink project which continues to support python 2.7"
DESCRIPTION = "The project extension [-27] indicates that this project continues to support python 2.7, \
                as the streamlink project has discontinued python 2.7 support as of version 1.7.0"
HOMEPAGE = "https://billy2011.github.io/streamlink-27"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7c0be52291b7252b878da806d185b1d1"

RDEPENDS_${PN} = "python-core \
    python-backports-functools-lru-cache \
    python-backports-shutil-get-terminal-size \
    python-backports-shutil-which \
    python-ctypes \
    python-futures \
    python-isodate \
    python-iso3166 \
    python-iso639 \
    python-lxml \
    python-misc \
    python-pkgutil \
    python-pycryptodome \
    python-pysocks \
    python-requests \
    python-shell \
    python-singledispatch \
    python-subprocess \
    python-typing \
    python-websocket-client \
    python-youtube-dl \
    "

inherit gittag setuptools

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/oe-mirrors/streamlink-27;protocol=https"

S = "${WORKDIR}/git"

do_install_append() {
    rm -rf ${D}${bindir}
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/streamlink_cli
}

PACKAGES = "${PN}-src ${PN}"

FILES_${PN} = " \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*.pyo \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*.pyo \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*/*.pyo \
    "

FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink-*.egg-info/* \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/plugins/.removed \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/streamlink/*/*/*.py \
    "
