SUMMARY = "Download videos from YouTube (and more sites)"
DESCRIPTION = "youtube-dl is a small command-line program to download videos \
from YouTube.com and a few more sites. It requires the python interpreter \
(2.6, 2.7, or 3.2+), and it is not platform specific"
HOMEPAGE = "http://rg3.github.io/youtube-dl/"
SECTION = "devel/python"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7246f848faa4e9c9fc0ea91122d6e680"

DEPENDS = "libxml2 bash-completion"

inherit python3-dir setuptools3 gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/ytdl-org/youtube-dl.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

do_compile:prepend() {
    cd ${S}
    oe_runmake lazy-extractors youtube-dl.bash-completion
}

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/bash_completion.d
    mv ${D}${prefix}${sysconfdir}/bash_completion.d ${D}${sysconfdir}
    rm -rf ${D}${prefix}${sysconfdir}
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/youtube_dl*egg-info/PKG-INFO
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/youtube_dl*egg-info/SOURCES.txt
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/youtube_dl*egg-info/dependency_links.txt
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/youtube_dl*egg-info/top_level.txt
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/youtube_dl*dist-info/*
    chmod 755 ${D}/usr/bin/youtube-dl
}

RDEPENDS:${PN} = " \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-gdata-python3 \
    ${PYTHON_PN}-unixadmin \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-html \
    "

RDEPENDS:{PN}-src = "${PN}"
FILES:${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${datadir}/etc/* \
    "

FILES:${PN} += "${sysconfdir}"
