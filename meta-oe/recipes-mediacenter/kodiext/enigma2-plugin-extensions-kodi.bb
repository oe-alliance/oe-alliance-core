DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv autotools ${PYTHON_PN}native

SRCREV = "${AUTOREV}"
PV = "19+git${SRCPV}"
PKGV = "19+git${GITPKGV}"
VER = "19"

RDEPENDS_${PN} += "virtual/kodi"

RRECOMMENDS_${PN} = "${@bb.utils.contains("MACHINE_FEATURES", "no-subssupport", "" , "enigma2-plugin-extensions-subssupport", d)}"

SRC_URI = "git://github.com/oe-mirrors/kodiext;protocol=git;branch=master \
        file://0001-add-subtitleSelection-option.patch \
        file://advancedsettings.xml \
        "

S = "${WORKDIR}/git"

do_install_append() {
	install -d ${D}/usr/share/kodi/system
	install -m 0755 ${WORKDIR}/advancedsettings.xml ${D}/usr/share/kodi/system
}

FILES_${PN} = " \
    ${libdir}/enigma2/python/Plugins/Extensions/Kodi \
    ${bindir}/kodiext \
    /usr/share/kodi/system \
    "

INSANE_SKIP += "file-deps"
