SUMMARY = "Shows some properties of the TPM configuration area"
SECTION = "console/utils"
LICENSE = "CLOSED"
DEPENDS = "tpmd"
SRCREV = "87312149aceebfaa9ce69d7eaf4f71c57d37e77c"

inherit opendreambox-git

do_install() {
        oe_runmake install DESTDIR=${D}
}

RDEPENDS_${PN} = "tpmd"
