DESCRIPTION="display vfd"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "freetype libpng"

PR = "r5"
SRC_URI = "file://displayvfd.tgz"

S = "${WORKDIR}/displayvfd"

CXXFLAGS += "-I${STAGING_INCDIR}/freetype2/ -I${STAGING_INCDIR}"
LDFLAGS += "-lfreetype -lpng"

inherit autotools pkgconfig

do_install() {
    install -d ${D}${bindir}
    install -m 0755 displayvfd ${D}${bindir}
    
}

FILES_${PN} = "${bindir}"

