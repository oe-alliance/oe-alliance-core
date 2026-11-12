SUMMARY = "Elektro Enigma2 skin"
DESCRIPTION = "Dark navy OpenATV skin with service information and a blue-highlighted interface."
LICENSE = "CLOSED"
SECTION = "base"
PRIORITY = "optional"
PV = "1.6"
PR = "r0"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://skin.xml file://servisnik.png"
S = "${WORKDIR}"

inherit allarch

RDEPENDS:${PN} = "enigma2"

do_install() {
    install -d ${D}${datadir}/enigma2/Elektro
    install -m 0644 ${WORKDIR}/skin.xml ${D}${datadir}/enigma2/Elektro/skin.xml
    install -m 0644 ${WORKDIR}/servisnik.png ${D}${datadir}/enigma2/Elektro/servisnik.png
}

FILES:${PN} = "${datadir}/enigma2/Elektro"
