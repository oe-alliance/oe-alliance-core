MODULE = "PictureCenterFS"
DESCRIPTION = "Show, manage and edit pictures and slideshows on enigma2."
MAINTAINER = "jbleyel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=11a9969bdf1bc48a06dad1fd82c61bf9"

DEPENDS = "enigma2"
RDEPENDS:${PN} = "${PYTHON_PN}-core"

inherit ${PYTHON_PN}-dir gitpkgv ${PYTHON_PN}native gettext

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/PictureCenterFS.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

# Just a quick hack to "compile" it
do_compile() {
    cd ${S}
    for f in $(find ./po -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*po\///')
        mkdir -p ${S}/locale/${l%}/LC_MESSAGES
        msgfmt -o ${S}/locale/${l%}/LC_MESSAGES/PlanerFS.mo ./po/$l.po
    done
    cd -
}

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/${MODULE}"
do_install:append() {
    install -d ${D}${PLUGINPATH}
    cp -rp ${S}/* ${D}${PLUGINPATH}
}

require conf/python/python3-compileall.inc

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.pot$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

do_package_qa[noexec] = "1"
