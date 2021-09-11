SUMMARY = "Download videos from YouTube (and more sites)"
DESCRIPTION = "youtube-dl is a small command-line program to download videos \
from YouTube.com and a few more sites. It requires the Python interpreter \
(2.6, 2.7, or 3.2+), and it is not platform specific"
HOMEPAGE = "http://rg3.github.io/youtube-dl/"
SECTION = "devel/python"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7246f848faa4e9c9fc0ea91122d6e680"

DEPENDS = "libxml2 bash-completion"

inherit setuptools gittag

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/ytdl-org/youtube-dl.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

do_compile_prepend() {
    cd ${S}
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
    python-html \
    "

RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    ${datadir}/etc/* \
    "

FILES_${PN} += "${sysconfdir}"
