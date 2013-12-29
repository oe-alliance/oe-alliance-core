SUMMARY = "Azbox IPTV plugin"
RDEPENDS_${PN} = "enigma2"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE="CLOSED"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r6"

SRC_URI = "git://github.com/OpenAZBox/AZIPTV.git;protocol=git"

S = "${WORKDIR}/git"

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
    install -d  ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV
    
    install -m 0644 ${S}/*.pyo \
    ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV

    install -m 0755 ${S}/config \
    ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Picons/
        install -m 0644 ${S}/Picons/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Picons/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Ico/
        install -m 0644 ${S}/Ico/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Ico/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Lists/
        install -m 0755 ${S}/Lists/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV/Lists/
}

FILES_{$PN} = "/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV"

PACKAGES = "enigma2-plugin-extensions-aziptv"

PROVIDES="${PACKAGES}"
