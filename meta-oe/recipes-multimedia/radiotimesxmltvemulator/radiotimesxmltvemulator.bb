DESCRIPTION = "Handle your EPG on enigma2 using opentv and xmltv"
HOMEPAGE = "https://github.com/LraiZer/RadiotimesXmltvEmulator"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.0+gitr${SRCPV}"
PKGV = "1.0.0+gitr${GITPKGV}"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

INSANE_SKIP_${PN} += "already-stripped ldflags"

SRC_URI = "git://github.com/LraiZer/RadiotimesXmltvEmulator.git;branch=e2xmltv;protocol=git"

S = "${WORKDIR}/git"

do_compile() {
    echo ${PV} > ${S}/VERSION
    oe_runmake SWIG="swig"
}

do_install() {
    oe_runmake 'D=${D}' install
}

FILES_${PN}_append = " /usr"
