DESCRIPTION = "Enigma2 plugin to launch and play media from Kodi"
AUTHOR = "Maroš Ondrášek <mx3ldev@gmail.com>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "19"

RDEPENDS_${PN} += "virtual/kodi"

RRECOMMENDS_${PN} = "${@bb.utils.contains("MACHINE_FEATURES", "no-subssupport", "" , "enigma2-plugin-extensions-subssupport", d)}"

SRCREV = "491bf29f3810d1beef20484b7886bcd0724aabc6"
SRC_URI = "git://github.com/oe-alliance/kodiext.git;protocol=https;branch=python3 \
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

inherit autotools
INSANE_SKIP += "file-deps"
