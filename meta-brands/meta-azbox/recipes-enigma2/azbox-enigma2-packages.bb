SUMMARY = "Azbox Specific plugin"
RDEPENDS_${PN} = "enigma2"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE="CLOSED"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r16"

SRC_URI = "git://github.com/OpenAZBox/RTi-SYS.git;protocol=git \
      file://VideoSettingsSetup \
     "

S = "${WORKDIR}"

do_compile() {
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

do_install() {
    install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS
    
    install -m 0644 ${S}/git/*.pyo \
    ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS

    install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup

    install -m 0644 ${S}/VideoSettingsSetup/*.pyo \
    ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup
}

FILES_enigma2-plugin-systemplugins-rtisys = "/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS"
FILES_enigma2-plugin-systemplugins-videosettingssetup = "/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup"

PACKAGES = "\
    enigma2-plugin-systemplugins-rtisys \    
    enigma2-plugin-systemplugins-videosettingssetup \
    "

PROVIDES="${PACKAGES}"
