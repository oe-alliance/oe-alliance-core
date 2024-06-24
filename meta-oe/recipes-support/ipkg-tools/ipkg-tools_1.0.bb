SUMMARY = "ipkg-build -- construct a .ipk from a directory"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Carl Worth <cworth@east.isi.edu>"

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "binutils"

S = "${WORKDIR}"

SRC_URI="file://ipkg-tools.zip"

SRC_URI[md5sum] = "ce8e784217d236f4d91b24072597e393"
SRC_URI[sha256sum] = "ea0d794f83d6020e5fcbc6bf0d0852882603f53d6760d5a416602df736e083ea"

FILES:${PN} = "/usr/bin/"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/ipkg-build ${D}/usr/bin/ipkg-build
    install -m 0755 ${WORKDIR}/ipkg-unbuild ${D}/usr/bin/ipkg-unbuild
}

INSANE_SKIP:${PN} ="file-rdeps"
