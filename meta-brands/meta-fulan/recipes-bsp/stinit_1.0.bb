DESCRIPTION = "STM Specific init script"
LICENSE = "CLOSED"

PR = "r2"

RDEPENDS_${PN} = "ustslave"

SRC_URI = "file://init.sh4"

FILES_${PN} = "/bin"

do_install () {
    install -d ${D}/bin
    install -m 755 ${WORKDIR}/init.sh4 ${D}/bin
}

# Hack to avoid conflicts with old systemv package
pkg_postinst_${PN}() {
    mv /bin/init.sh4 /bin/devinit
}

