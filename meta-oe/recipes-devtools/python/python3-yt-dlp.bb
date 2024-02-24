SUMMARY = "A youtube-dl fork with additional features and fixes"
DESCRIPTION = "yt-dlp is a youtube-dl fork based on the now inactive youtube-dlc. \
The main focus of this project is adding new features and patches while also keeping \
up to date with the original project."
HOMEPAGE = "https://github.com/yt-dlp/yt-dlp"
SECTION = "devel/python"
LICENSE = "Unlicense"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7246f848faa4e9c9fc0ea91122d6e680"

DEPENDS = "libxml2 bash-completion"

inherit python3-dir python_hatchling gittag


SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/yt-dlp/yt-dlp;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

do_compile:prepend() {
    cd ${S}
    oe_runmake lazy-extractors yt-dlp completion-bash
}

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/bash_completion.d
    install -m 0644 ${S}/completions/bash/yt-dlp ${D}${sysconfdir}/bash_completion.d/yt-dlp.bash-completion
    rm -f ${D}${libdir}/${PYTHON_DIR}/site-packages/yt_dlp*egg-info/PKG-INFO
    rm -f ${D}${libdir}/${PYTHON_DIR}/site-packages/yt_dlp*egg-info/SOURCES.txt
    rm -f ${D}${libdir}/${PYTHON_DIR}/site-packages/yt_dlp*egg-info/dependency_links.txt
    rm -f ${D}${libdir}/${PYTHON_DIR}/site-packages/yt_dlp*egg-info/top_level.txt
    rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/yt_dlp*dist-info/*
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
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    ${datadir} \
    "

FILES:${PN} += "${sysconfdir}"
