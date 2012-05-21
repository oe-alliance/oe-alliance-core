S = "${WORKDIR}/git"

inherit gitpkgv

VERSION := "${PV}"
PV = "${VERSION}+git${SRCPV}"
PKGV = "${VERSION}+git${GITPKGV}"
