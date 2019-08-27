require conf/license/license-gplv2.inc
inherit allarch

RDEPENDS_${PN} += "showiframe"

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${datadir}/bootlogos/${BOOTLOGONAME}
    install -m 0644 ${S}/${BOOTLOGONAME}/bootlogo.mvi ${D}${datadir}/bootlogos/${BOOTLOGONAME}/bootlogo.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/bootlogo_wait.mvi ${D}${datadir}/bootlogos/${BOOTLOGONAME}/bootlogo_wait.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/backdrop.mvi ${D}${datadir}/bootlogos/${BOOTLOGONAME}/backdrop.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/prev.png ${D}${datadir}/bootlogos/${BOOTLOGONAME}/prev.png
}

FILES_${PN} = "${datadir}/bootlogos/"