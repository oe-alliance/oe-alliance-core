LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://mkbootimg/mkbootimg;endline=14;md5=6b2adf3438797b0894bda5698562b285"
SRCREV = "c39a31f002d291607126bd27b846408ee7030c04"

SRC_URI = "git://android.googlesource.com/platform/system/core;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 755 mkbootimg/mkbootimg ${D}${bindir}
}

RDEPENDS:${PN} = "python3-core"

BBCLASSEXTEND = "native"
