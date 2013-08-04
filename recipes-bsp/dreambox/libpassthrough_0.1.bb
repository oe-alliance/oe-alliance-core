SUMMARY = "Dreambox TS/M2TS audio passthrough helper lib"
LICENSE = "Proprietary"
PR = "r2"

SRC_URI[mips32el.md5sum] = "3e0dd274f1f8ed31df3ff7311c964cf9"
SRC_URI[mips32el.sha256sum] = "5428b6f672d119969145007c620c966fdc5740f72394e59c81e256a6cfa133ac"

inherit opendreambox-precompiled-binary

do_install() {
        install -d ${D}${libdir}
        install -m 0755 libpassthrough.so ${D}${libdir}
}

FILES_${PN} = "${libdir}"

DEBIAN_NOAUTONAME_${PN} = "1"
