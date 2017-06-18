require conf/license/license-gplv2.inc
inherit allarch

RDEPENDS_${PN} += "showiframe"

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share/bootlogos/${BOOTLOGONAME}
    install -m 0644 ${S}/${BOOTLOGONAME}/bootlogo.mvi ${D}/usr/share/bootlogos/${BOOTLOGONAME}/bootlogo.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/bootlogo_wait.mvi ${D}/usr/share/bootlogos/${BOOTLOGONAME}/bootlogo_wait.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/backdrop.mvi ${D}/usr/share/bootlogos/${BOOTLOGONAME}/backdrop.mvi
    install -m 0644 ${S}/${BOOTLOGONAME}/prev.png ${D}/usr/share/bootlogos/${BOOTLOGONAME}/prev.png
}

FILES_${PN} = "/usr/share/bootlogos/"