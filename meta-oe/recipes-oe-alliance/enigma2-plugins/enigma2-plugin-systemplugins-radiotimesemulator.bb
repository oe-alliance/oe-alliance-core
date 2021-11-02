DESCRIPTION = "Handle your EPG on enigma2 using opentv and xmltv"
HOMEPAGE = "https://github.com/LraiZer/RadiotimesXmltvEmulator"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.0+gitr${SRCPV}"
PKGV = "1.0.0+gitr${GITPKGV}"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

INSANE_SKIP_${PN} += "already-stripped ldflags build-deps"

SRC_URI = "git://github.com/LraiZer/RadiotimesXmltvEmulator.git;branch=gui-plugin;protocol=https"

S = "${WORKDIR}/git"

do_compile() {
    echo ${PV} > ${S}/VERSION
    oe_runmake SWIG="swig"
}

do_install() {
    oe_runmake 'D=${D}' install-plugin
}

pkg_postrm_${PN}() {
rm -fr ${libdir}/enigma2/python/Plugins/SystemPlugins/RadiotimesXmltvEmulator > /dev/null 2>&1
}

# Just a quick hack to "compile" the python parts.
do_compile_append() {
    python -O -m compileall ${S}
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

FILES_${PN}_append = " /usr"
