DESCRIPTION = "STM Specific init script"
LICENSE = "CLOSED"

PR = "r1"

RDEPENDS_${PN} = "ustslave"

SRC_URI = "file://devinit"

FILES_${PN} = "/bin"

do_install () {
    install -d ${D}/bin
    install -m 755 ${WORKDIR}/devinit ${D}/bin
}

