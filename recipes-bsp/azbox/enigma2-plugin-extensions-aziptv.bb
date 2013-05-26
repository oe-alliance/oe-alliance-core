DESCRIPTION = "Azbox IPTV plugin"
RDEPENDS = "enigma2"
DEPENDS = "python-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE="CLOSED"

SRCREV_pn-${PN} ?= "${AUTOREV}"

inherit gitpkgv pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r5"

SRC_URI = "git://github.com/OpenAZBox/AZIPTV.git;protocol=git"

S = "${WORKDIR}/git"

do_install() {
	install -d  ${D}/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV
	
	install -m 0644 ${S}/*.py \
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

FILES_enigma2-plugin-extensions-aziptv = "/usr/lib/enigma2/python/Plugins/Extensions/AzIPTV"

PACKAGES = "enigma2-plugin-extensions-aziptv"

PROVIDES="${PACKAGES}"
