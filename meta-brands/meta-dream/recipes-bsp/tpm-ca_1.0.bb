SUMMARY = "Shows some properties of the TPM configuration area"
SECTION = "console/utils"
require conf/license/license-close.inc

DEPENDS = "tpmd"

SRCREV = "${AUTOREV}"

inherit opendreambox-git

do_install() {
        oe_runmake install DESTDIR=${D}
}

RDEPENDS:${PN} = "tpmd"
