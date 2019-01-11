DESCRIPTION = "Allows to list directory"
MAINTAINER = "samsamsam"

require conf/license/license-gplv2.inc

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/atvcaptain/lsdir.git;protocol=git"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/"

SOURCE_FILES = "src/lsdir.c"

do_compile() {
    ${CC} ${SOURCE_FILES} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE=1 -D_LARGEFILE_SOURCE -I${S}/src -I${D}/${libdir} -I${D}/${includedir} -o lsdir ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/lsdir ${D}${bindir}
}
