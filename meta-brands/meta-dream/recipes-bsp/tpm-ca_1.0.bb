SUMMARY = "Shows some properties of the TPM configuration area"
SECTION = "console/utils"
LICENSE = "CLOSED"

DEPENDS = "tpmd"

SRCREV = "${AUTOREV}"

inherit opendreambox-git

do_install() {
        oe_runmake install DESTDIR=${D}
}

RDEPENDS:${PN} = "tpmd"
