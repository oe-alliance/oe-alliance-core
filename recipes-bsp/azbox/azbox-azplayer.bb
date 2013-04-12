DESCRIPTION = "Azbox AZplayer app plugin"
RDEPENDS = "enigma2 curl"
LICENSE = "CLOSED"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S = "${WORKDIR}/git"



PR = "r11"
inherit gitpkgv

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
SRC_URI = "git://github.com/OpenAZBox/AZPlay.git" 

do_install_azboxhd() {
	install -d ${D}/usr/bin/
	install -m 0755 ${S}/bin/rmfp_player-ForHD ${D}/usr/bin/rmfp_player

	install -d ${D}/usr/lib/
	install -m 0755 ${S}/lib/lib* ${D}/usr/lib/

	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
	install -m 0755 ${S}/plugin/*.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
        install -m 0755 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

do_install_azboxme() {
	install -d ${D}/usr/bin/
	install -m 0755 ${S}/bin/rmfp_player ${D}/usr/bin/

	install -d ${D}/usr/lib/
	install -m 0755 ${S}/lib/lib* ${D}/usr/lib/

	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/
	install -m 0755 ${S}/plugin/*.py ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/

        install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
        install -m 0755 ${S}/img/*.png ${D}/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/
}

do_install_azboxminime() {
do_install_azboxme
}

FILES_${PN} = "/usr/bin/"
FILES_${PN} += "/usr/lib/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/"
FILES_${PN} += "/usr/lib/enigma2/python/Plugins/Extensions/AZPlay/img/"



