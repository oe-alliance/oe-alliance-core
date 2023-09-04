DESCRIPTION="display vfd"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "libpng"

RCONFLICTS:${PN} = "vuplus-displayvfd"
RREPLACES:${PN}  = "vuplus-displayvfd"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PKGV = "1.0+git${GITPKGV}"
PV = "1.0+git"

SRC_URI = "git://github.com/oe-alliance/displayvfd.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

CXXFLAGS += "-I${STAGING_INCDIR}"
LDFLAGS += "-lpng"

inherit autotools pkgconfig

do_install() {
    install -d ${D}${bindir}
    install -m 0755 displayvfd ${D}${bindir}
    
}

FILES:${PN} = "${bindir}"
