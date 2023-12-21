DESCRIPTION = "Box logos for Enimga2."
MAINTAINER = "DimitarCC"
HOMEPAGE = "https://github.com/DimitarCC/e2-boxlogos"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

ALLOW_EMPTY:${PN} = "1"

inherit gitpkgv  ${PYTHON_PN}native

SRCREV = "${AUTOREV}"

PV = "1.0+gitr${SRCPV}"
PKGV = "1.0+gitr${GITPKGV}"

do_configure[nostamp] = "1"

SRC_URI = "git://github.com/DimitarCC/e2-boxlogos.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${datadir}/enigma2/logos
    if [ -f "${S}/box/${MACHINEBUILD}.svg" ] ; then
        install -m 0644 "${S}/box/${MACHINEBUILD}.svg" ${D}${datadir}/enigma2/logos/boxlogo.svg
    fi
    if [ -f "${S}/brand/${MACHINE_BRAND}.svg" ] ; then
        install -m 0644 "${S}/brand/${MACHINE_BRAND}.svg" ${D}${datadir}/enigma2/logos/brandlogo.svg
    fi
    if [ -f "${S}/distro/${DISTRO_NAME}.svg" ] ; then
        install -m 0644 "${S}/distro/${DISTRO_NAME}.svg" ${D}${datadir}/enigma2/logos/distrologo.svg
    fi
    install -d ${D}${libdir}/enigma2/python/Components/Renderer
    cp -r ${S}/Renderer/* ${D}${libdir}/enigma2/python/Components/Renderer
    python3 -m compileall -o2 -b ${D}${libdir}/enigma2/python/Components/Renderer
}

FILES:${PN} = "${datadir}/enigma2/logos ${libdir}/enigma2/python/Components/Renderer/"
FILES:${PN}-src = "${libdir}/enigma2/python/Components/Renderer/*.py"
