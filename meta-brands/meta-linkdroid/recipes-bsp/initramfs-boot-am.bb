SUMMARY = "Extremely basic live image init script for amlogic"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://init"

PR = "r0"

do_install() {
        install -m 0755 ${WORKDIR}/init  ${D}/init
}

inherit allarch

FILES_${PN} += " /init "
