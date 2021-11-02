SUMMARY = "Multi-platform Super Nintendo emulator (SDL version)"
HOMEPAGE = "http://www.snes9x.com/"
SECTION = "emulators"

LICENSE = "GPLv2 & LGPLv2.1" 
LIC_FILES_CHKSUM = "file://../docs/snes9x-license.txt;md5=2990ee23aa20730e9a67366f467e0991 \
                    file://../docs/gpl-2.0.txt;md5=751419260aa954499f7abaabaa882bbe \
                    file://../docs/lgpl-2.1.txt;md5=243b725d71bb5df4a1e5920b344b86ad" 

SRC_URI = "git://github.com/emulatorE2/snes9x-sdl.git;protocol=https"

inherit gitpkgv autotools-brokensep

SRCREV = "${AUTOREV}"
PV = "1.53+git${SRCPV}"
PKGV = "1.53+git${GITPKGV}"
VER ="1.53"

S = "${WORKDIR}/git/sdl"

DEPENDS = "libsdl"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 snes9x-sdl ${D}${bindir}
}
