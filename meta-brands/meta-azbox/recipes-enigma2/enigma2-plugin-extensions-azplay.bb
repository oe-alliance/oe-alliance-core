SUMMARY = "Azbox AZplayer app plugin"
RDEPENDS_${PN} = "enigma2 fuse libupnp azbox-mrua azbox-compat"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_${PN} = "curl fuse libupnp djmount libjpeg-turbo libpng"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r12"

SRC_URI = "git://github.com/OpenAZBox/AZPlay.git;protocol=git"

S = "${WORKDIR}/git"

do_compile() {
    python -O -m compileall ${S}
}

do_populate_sysroot[noexec] = "1"

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

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
    install -m 0644 ${S}/plugin/*.pyo ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
    install -m 0644 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${S}/bin/rmfp_player ${D}/usr/bin/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
    install -m 0644 ${S}/plugin/*.pyo ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
    install -m 0644 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

do_package_qa() {
}

FILES_${PN} = "/usr/bin/"
FILES_${PN} += "/usr/lib/"
FILES_${PN} += "/etc/init.d/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/"

PACKAGES = "enigma2-plugin-extensions-azplay"
PROVIDES="${PACKAGES}"
