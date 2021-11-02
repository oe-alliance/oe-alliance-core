SUMMARY = "AirTunes protocol server"
DEPENDS = "openssl"
LICENSE = "GPLv2"

DEPENDS = "alsa-lib openssl"

inherit gitpkgv pkgconfig

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
SRCREV ?= "${AUTOREV}"

require conf/license/license-gplv2.inc

SRC_URI = "git://github.com/skaman/shairport.git;protocol=https"

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

INSANE_SKIP_${PN} += "already-stripped ldflags"

