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
PV = "git"
PKGV = "${GITPKGVTAG}"
PR = "r2"

SRC_URI = "git://github.com/yt-dlp/yt-dlp;protocol=https;branch=master"

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

require recipes-devtools/python/python3-yt-dlp-ejs.inc

# yt-dlp enables only deno by default, and no receiver has deno. Make quickjs
# the default, both for the command line and for the library entry point.
do_compile:prepend() {
    sed -i "s/self.params.get('js_runtimes', {'deno': {}})/self.params.get('js_runtimes', {'quickjs': {}})/" ${S}/yt_dlp/YoutubeDL.py
    sed -i "s/default=\['deno'\],/default=['quickjs'],/" ${S}/yt_dlp/options.py
    grep -q "js_runtimes', {'quickjs': {}}" ${S}/yt_dlp/YoutubeDL.py || bbfatal "js_runtimes default in YoutubeDL.py no longer matches"
    grep -q "default=\['quickjs'\]," ${S}/yt_dlp/options.py || bbfatal "--js-runtimes default in options.py no longer matches"

    # yt-dlp accepts only one version of the solver package.
    ejs_pv=$(sed -n 's/.*"yt-dlp-ejs==\([^"]*\)".*/\1/p' ${S}/pyproject.toml | head -1)
    if [ -z "$ejs_pv" ]; then
        bbfatal "no yt-dlp-ejs pin found in pyproject.toml, the solver check needs a new anchor"
    elif [ "$ejs_pv" != "${YT_DLP_EJS_PV}" ]; then
        bbfatal "yt-dlp wants yt-dlp-ejs $ejs_pv, python3-yt-dlp-ejs carries ${YT_DLP_EJS_PV}"
    fi

    # Cosmetic, and deliberately unguarded: a reworded help text must not fail the build.
    sed -i 's/Only "deno" is enabled by default/Only "quickjs" is enabled by default/' ${S}/yt_dlp/options.py
    sed -i 's/Only deno is enabled by default/Only quickjs is enabled by default/' ${S}/yt_dlp/extractor/youtube/_video.py

    cd ${S}
    oe_runmake lazy-extractors yt-dlp completion-bash
}

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/bash_completion.d
    install -m 0644 ${S}/completions/bash/yt-dlp ${D}${sysconfdir}/bash_completion.d/yt-dlp.bash-completion
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/yt_dlp*egg-info/PKG-INFO
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/yt_dlp*egg-info/SOURCES.txt
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/yt_dlp*egg-info/dependency_links.txt
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/yt_dlp*egg-info/top_level.txt
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/yt_dlp*dist-info/*
}

RDEPENDS:${PN} = " \
    python3-email \
    python3-gdata-python3 \
    python3-unixadmin \
    python3-ctypes \
    python3-html \
    python3-yt-dlp-ejs \
    quickjs \
    "

RDEPENDS:${PN}-src = "${PN}"
FILES:${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${datadir} \
    "

FILES:${PN} += "${sysconfdir}"
