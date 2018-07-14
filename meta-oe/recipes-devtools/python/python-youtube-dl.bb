SUMMARY = "Download videos from YouTube (and more sites)"
DESCRIPTION = "youtube-dl is a small command-line program to download videos \
from YouTube.com and a few more sites. It requires the Python interpreter \
(2.6, 2.7, or 3.2+), and it is not platform specific"
HOMEPAGE = "http://rg3.github.io/youtube-dl/"
SECTION = "devel/python"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7246f848faa4e9c9fc0ea91122d6e680"
DEPENDS = "libxml2 python-html bash-completion"
SRCREV = "40a051fa9f48000f311f243c40e3cae588420738"
PV = "2018.07.10"

PR = "r1"

SRC_URI = "git://github.com/rg3/youtube-dl.git;branch=master"

S = "${WORKDIR}/git"

inherit setuptools

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

do_compile_prepend() {
    oe_runmake lazy-extractors youtube-dl.bash-completion
}

do_install_append() {
    mv ${D}${datadir}/etc ${D}${sysconfdir}
    install -m 0755 -d ${D}${sysconfdir}/bash_completion.d
    install -m 0644 youtube-dl.bash-completion ${D}${sysconfdir}/bash_completion.d
}

RDEPENDS_${PN} = " \
    python-email \
    python-gdata \
    python-subprocess \
    python-unixadmin \
    python-ctypes \
    python-argparse \
    "

PACKAGES =+ " ${PN}-src"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    ${datadir}/etc/* \
    "

FILES_${PN} += "${sysconfdir}"
