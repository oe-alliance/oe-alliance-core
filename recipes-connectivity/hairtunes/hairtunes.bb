DESCRIPTION = "AirTunes protocol server"
DEPENDS = "openssl"
LICENSE = "proprietary"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"
SRCREV ?= "${AUTOREV}"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ed539e41bdcf2568e838ae58205dc02d"

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

