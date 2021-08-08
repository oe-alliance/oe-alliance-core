DESCRIPTION = "STM Specific init script"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r3"

RDEPENDS:${PN} = "ustslave"

SRC_URI = "file://init.sh4"

FILES:${PN} = "/bin/devinit"

do_install () {
    install -d ${D}/bin
    install -m 755 ${WORKDIR}/init.sh4 ${D}/bin/devinit
}
