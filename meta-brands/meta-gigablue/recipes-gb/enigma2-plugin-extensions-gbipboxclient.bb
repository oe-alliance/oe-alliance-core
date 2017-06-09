SUMMARY = "GigaBlue IPBox Client"
MAINTAINER = "GigaBlue"
SECTION = "base"
LICENSE = "GPLv2"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI="git://github.com/openmips/gbremote-client.git"

S = "${WORKDIR}/git"

inherit autotools-brokensep

DEPENDS = "python"

RCONFLICTS_${PN} = "enigma2-plugin-systemplugins-gbipboxclient"
RREPLACES_${PN} = "enigma2-plugin-systemplugins-gbipboxclient"

EXTRA_OECONF = "\
    --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

do_install_append() {
    # remove unused .pyc files
    find ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/ -name '*.pyc' -exec rm {} \;
    
    # remove helper .pyo file
    find ${D}/usr/lib/enigma2/python/Plugins/Extensions/${PLUGIN}/ -name '*helper.pyo' -exec rm {} \;
}

# skip this!
install_egg_info() {
}

do_configure_prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(?!.*helper\.py).*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
