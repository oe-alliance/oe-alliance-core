DESCRIPTION = "Converts TS ETI V.11 streams to ETI NI G.703"
require conf/license/license-gplv2.inc
inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/satdreamgr/tsniv2ni.git;protocol=https;branch=master \
          file://0001-fix-error-reference-to-size-is-ambiguous.patch \
"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

TARGET_CC_ARCH += "${LDFLAGS}"

do_configure[noexec] = "1"
