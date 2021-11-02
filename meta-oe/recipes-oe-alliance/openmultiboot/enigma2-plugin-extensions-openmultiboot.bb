SUMMARY = "Multi boot loader for enigma2"
MAINTAINER = "oe-alliance"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv pythonnative gettext

SRCREV = "${AUTOREV}"
PV = "1.3+git${SRCPV}"
PKGV = "1.3+git${GITPKGV}"
PR = "r0"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/oe-alliance/openmultibootmanager.git;protocol=https;branch=dev-bootmenu-helper"
S = "${WORKDIR}/git"

inherit autotools-brokensep

DEPENDS = "python lzo"

RDEPENDS_${PN} = "kernel-module-nandsim openmultiboot enigma2 lzo"

RDEPENDS_${PN}_gb800solo = "kernel-module-block2mtd openmultiboot"
RDEPENDS_${PN}_spark7162 = "kernel-module-block2mtd openmultiboot unjffs2"
RDEPENDS_${PN}_spark = "kernel-module-block2mtd openmultiboot unjffs2"

EXTRA_OECONF = "\
    --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-arch=${TARGET_ARCH} \
    "

do_install_append() {
    # remove unused .pyc files
    find ${D}${libdir}/enigma2/python/Plugins/Extensions/${PLUGIN}/ -name '*.pyc' -exec rm {} \;

    # remove .pyo files
    find ${D}${libdir}/enigma2/python/Plugins/Extensions/${PLUGIN}/ -name '*.pyo' -exec rm {} \;
    chmod 755 ${D}/usr/lib/enigma2/python/Plugins/Extensions/OpenMultiboot/open-multiboot-branding-helper.py
    chmod 755 ${D}/usr/lib/enigma2/python/Plugins/Extensions/OpenMultiboot/open-multiboot-menu-helper.py
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

pkg_preinst_${PN}() {
#!/bin/sh
if mountpoint -q ${libdir}/enigma2/python/Plugins/Extensions/OpenMultiboot; then
    echo "openMultiBoot will only install on main image."
    echo "Child image is running - canceling installation!"
    sleep 3
    exit 1
else
    echo "Main image is running - proceeding installation..."
    exit 0
fi
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

INSANE_SKIP_${PN} = "already-stripped"
