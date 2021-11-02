# add to oe-alliance-core/meta-oe/recipes-distros/openvix/skins/enigma2-plugin-skins-vix-turquoise-hd.bb
# add "enigma2-plugin-skins-vix-turquoise-hd" as DEPENDS in oe-alliance-core/meta-oe/recipes-distros/openvix/image/openvix-feeds.bb

DESCRIPTION = "ViX-Turquoise-HD for OpenViX"
MAINTAINER = "norhap"
require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI = "git://github.com/norhap/Turquoise-HD.git;protocol=https"
S = "${WORKDIR}/git"

FILES_${PN} += " ${datadir}/enigma2 ${libdir}/enigma2/python/Components/Converter/BTVInfo.pyo ${libdir}/enigma2/python/Components/Converter/BTVDevicesInfo.pyo ${libdir}/enigma2/python/Components/Converter/BTVCpuUsage.pyo ${libdir}/enigma2/python/Components/Converter/BTVRunning14Events.pyo ${libdir}/enigma2/python/Components/Renderer/DRRunningText.pyo ${libdir}/enigma2/python/Components/Renderer/poster.pyo"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

do_compile() {
python -O -m compileall ${S}
}

do_install() {
        find ${S}/usr/lib/enigma2/python/Components/ -name "*.py" -exec rm -rf {} \;
	install -d ${D}/usr/share
	cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
        install -d ${D}${libdir}/enigma2/python/Components
        cp -rp ${S}/usr/lib/enigma2/python/Components/* ${D}${libdir}/enigma2/python/Components
}

FILES_${PN}-src = "\
    ${libdir}/enigma2/python/*.py \
    ${libdir}/enigma2/python/*/*.py \
    ${libdir}/enigma2/python/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*/*.py \
    "

do_package_qa[noexec] = "1"
