SUMMARY = "Dreambox TS/M2TS audio passthrough helper lib"
LICENSE = "Proprietary"
PR = "r0"

SRC_URI[mips32el.md5sum] = "9b7234d88a54e2ec0d27f3cd3af1fb06"
SRC_URI[mips32el.sha256sum] = "6c9051ed54b720ee326ff5c2abf16aa29dddd88944ad98a0c15e996529d22a22"

inherit opendreambox-precompiled-binary

do_install() {
        install -d ${D}${libdir}
        install -m 0755 libpassthrough.so ${D}${libdir}
}

FILES_${PN} = "${libdir}"

DEBIAN_NOAUTONAME_${PN} = "1"
