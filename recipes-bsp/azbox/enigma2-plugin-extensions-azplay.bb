SUMMARY = "Azbox AZplayer app plugin"
RDEPENDS_${PN} = "enigma2 curl fuse libupnp"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE = "CLOSED"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r10"

SRC_URI = "git://github.com/OpenAZBox/AZPlay.git;protocol=git"

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

do_install_azboxhd() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/bin/rmfp_player-ForHD ${D}/usr/bin/rmfp_player

    install -m 0755 ${S}/bin/djmount ${D}/usr/bin/

    install -d ${D}/etc/init.d
    install -m 0755 ${S}/bin/init ${D}/etc/init.d/djmount


    install -d ${D}/usr/lib/
    install -m 0755 ${S}/lib/lib* ${D}/usr/lib/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
    install -m 0644 ${S}/plugin/*.pyo ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
        install -m 0644 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/bin/rmfp_player ${D}/usr/bin/

    install -m 0755 ${S}/bin/djmount ${D}/usr/bin/

    install -d ${D}/etc/init.d
    install -m 0755 ${S}/bin/init ${D}/etc/init.d/djmount


    install -d ${D}/usr/lib/
    install -m 0755 ${S}/lib/lib* ${D}/usr/lib/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
    install -m 0644 ${S}/plugin/*.pyo ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
        install -m 0644 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

FILES_${PN} = "/usr/bin/"
FILES_${PN} += "/usr/lib/"
FILES_${PN} += "/etc/init.d/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/"

