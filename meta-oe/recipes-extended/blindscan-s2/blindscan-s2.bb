SUMMARY = "Blindscan dvb-s(2) satellites using stv090x devices"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README.md;md5=f084bf390249474bef1b8817e83757fa"

SRC_URI = "git://bitbucket.org/majortom/blindscan-s2.git;protocol=http \
    file://support-enigma2.patch \
    file://dvb-api3.patch \
"

inherit gitpkgv
SRCREV = "2989816405c72af5beb5f354809566170a0755af"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"

S = "${WORKDIR}/git/"

do_install () {
	install -d ${D}/${bindir}
	install -m 755 ${S}/blindscan-s2 ${D}/${bindir}
}

INSANE_SKIP_${PN} += "ldflags"