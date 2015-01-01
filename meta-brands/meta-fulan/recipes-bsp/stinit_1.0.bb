DESCRIPTION = "STM Specific init script"
LICENSE = "CLOSED"

PR = "r3"

RDEPENDS_${PN} = "ustslave"

RCONFLICTS_${PN} += "sysvinit"

SRC_URI = "file://init.sh4"

FILES_${PN} = "/bin/devinit"

do_install () {
    install -d ${D}/bin
    install -m 755 ${WORKDIR}/init.sh4 ${D}/bin/devinit
}
