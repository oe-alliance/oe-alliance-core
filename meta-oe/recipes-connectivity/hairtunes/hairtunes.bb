SUMMARY = "AirTunes protocol server"
DEPENDS = "openssl"
LICENSE = "GPLv2"

DEPENDS = "alsa-lib openssl"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
PR = "r0"
SRCREV ?= "${AUTOREV}"

require conf/license/license-gplv2.inc

SRC_URI = "git://github.com/skaman/shairport.git;protocol=git"

S = "${WORKDIR}/git"

FILES_${PN} = "${bindir}/*"

do_compile() {
    make -f Makefile.alsa hairtunes
    ${STRIP} ${S}/hairtunes
}

do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/hairtunes ${D}/${bindir}
}

INSANE_SKIP_${PN} += "ldflags"

