DESCRIPTION = "Azbox Specific plugin"
RDEPENDS = "enigma2"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE="CLOSED"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r14"

SRC_URI = "git://github.com/OpenAZBox/RTi-SYS.git;protocol=git \
	  file://VideoSettingsSetup \
	 "

S = "${WORKDIR}"

do_install() {
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS
	install -m 0644 ${S}/git/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS
	install -d  ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup
	install -m 0644 ${S}/VideoSettingsSetup/*.py \
	${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup
}

FILES_enigma2-plugin-systemplugins-rtisys = "/usr/lib/enigma2/python/Plugins/SystemPlugins/RtiSYS"
FILES_enigma2-plugin-systemplugins-videosettingssetup = "/usr/lib/enigma2/python/Plugins/SystemPlugins/VideoSettingsSetup"

PACKAGES = "\
	enigma2-plugin-systemplugins-rtisys \
	enigma2-plugin-systemplugins-videosettingssetup \
	"

PROVIDES="${PACKAGES}"
