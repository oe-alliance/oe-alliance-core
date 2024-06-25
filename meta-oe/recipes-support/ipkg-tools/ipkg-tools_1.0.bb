SUMMARY = "ipkg-build -- construct a .ipk from a directory"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Carl Worth <cworth@east.isi.edu>"

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "binutils zstd"

S = "${UNPACKDIR}"

SRC_URI="file://ipkg-tools.zip"

SRC_URI[md5sum] = "17da94f251f052958f3c5c2bb4ef7a1d"
SRC_URI[sha256sum] = "162c404baa3e17e9b54d4be71d5bb8d7e871ee09339c838b0234eccf15e59119"

FILES:${PN} = "/usr/bin/"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${UNPACKDIR}/ipkg-build ${D}/usr/bin/ipkg-build
    install -m 0755 ${UNPACKDIR}/ipkg-unbuild ${D}/usr/bin/ipkg-unbuild
}

INSANE_SKIP:${PN} ="file-rdeps"
