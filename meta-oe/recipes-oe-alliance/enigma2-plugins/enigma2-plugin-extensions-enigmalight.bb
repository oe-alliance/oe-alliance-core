SUMMARY = "Enigmalight An Ambilight clone for broadcom based linux receivers."
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=784d7dc7357bd924e8d5642892bf1b6b"

inherit autotools-brokensep gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.2+git${SRCPV}"
PKGV = "0.2+git${GITPKGV}"
PR = "r4"

DEPENDS = "libusb python-native"

do_populate_sysroot[noexec] = "1"

do_package_qa() {
}

SRC_URI="git://github.com/rossi2000/enigmalight.git;protocol=git"

S = "${WORKDIR}/git/build"

OE_EXTRACONF = "\
    ${@base_contains('TARGET_FPU', 'soft', 'CPPFLAGS=-msoft-float', 'CPPFLAGS=-mhard-float', d)} \
    "

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/src/enigmalight ${D}/usr/bin/
    install -d ${D}/home/elight-addons
    rm ${WORKDIR}/git/elight-addons/usr/bin/elighttalk
    cp -aRf ${WORKDIR}/git/elight-addons/* ${D}/home/elight-addons
    install -m 0755 ${S}/src/elighttalk ${D}/home/elight-addons/usr/bin/elighttalk
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight
    cp -aRf ${S}/python/plugin/EnigmaLight/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight
}

do_compile_append() {
    python -O -m compileall ${S}
}

python populate_packages_prepend () {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', 'Enigma2 Plugin: %s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
}

FILES_${PN} = "/"