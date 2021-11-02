DESCRIPTION = "cmdwrap split tool"
MAINTAINER = "oe-a"

require conf/license/license-gplv2.inc

inherit gitpkgv

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/oe-mirrors/e2iplayer-deps.git;protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/cmdwrap"

SOURCE_FILES = "src/cmdwrap.c"

do_compile() {
    ${CC} ${SOURCE_FILES} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE=1 -D_LARGEFILE_SOURCE -I${S}/src -I${D}/${libdir} -I${D}/${includedir} -o cmdwrap ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/cmdwrap ${D}${bindir}
}
