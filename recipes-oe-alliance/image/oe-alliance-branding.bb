DESCRIPTION = "OE-A Branding Lib for OE-A 2.0"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINEBUILD}"

DEPENDS = "python"

require conf/license/license-gplv2.inc

inherit gitpkgv autotools pythonnative

PACKAGES += " ${PN}-src"

SRCREV = "${AUTOREV}"
PV = "0.3+git${SRCPV}"
PKGV = "0.3+git${GITPKGV}"
PR = "r9"

SRC_URI="git://github.com/oe-alliance/branding-module.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-distro=${DISTRO_NAME} \
    --with-boxtype=${MACHINEBUILD} \
    --with-machineoem="${MACHINE_OEM}" \
    --with-machinebrand="${MACHINE_BRAND}" \
    --with-machinename="${MACHINE_NAME}" \
    --with-imageversion=${DISTRO_VERSION} \
    --with-imagebuild=${BUILD_VERSION} \
    --with-driverdate=${DRIVERSDATE} \
    "

do_configure_prepend() {
    if [ "${MACHINE_OEM}" = "vuplus" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/vuplus/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "xtrend" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/etxx00/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "evo" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/evo/evo-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "dags1" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/dags/dags-dvb-modules-7335.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "dags2" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/dags/dags-dvb-modules-7335-ci.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "dags3" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/dags/dags-dvb-modules-7356.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "gigablue" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/gigablue/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "odinm9" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-odinm9.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "odinm7" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-odinm7.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "e3hd" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-e3hd.bb | cut -b 12-19`		
    elif [ "${MACHINE}" = "inihde" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-inihde.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "inihdp" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-inihdp.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "inihdx" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-inihdx.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "xp1000" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/xp/xp-dvb-modules-xp1000.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "cube" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/cube/e2bmc-dvb-modules-cube.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "ebox5000" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ebox/ebox-dvb-modules-ebox5000.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "ebox5100" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ebox/ebox-dvb-modules-ebox5100.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "ebox7358" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ebox/ebox-dvb-modules-ebox7358.bb | cut -b 12-19`
    elif [ "${MACHINE_OEM}" = "eboxlumi" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ebox/ebox-dvb-modules-eboxlumi.bb | cut -b 12-19`		
    elif [ "${MACHINE}" = "ixussone" -o "${MACHINE}" = "ixusszero" -o "${MACHINE}" = "ixussduo" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ixuss/ixuss-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "azboxhd" -o "${MACHINE}" = "azboxme" -o "${MACHINE}" = "azboxminime" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/azbox/azbox-dvb-modules.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "sogno8800hd" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/sogno/sogno-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "dm500hd" -o "${MACHINE}" = "dm800se" -o "${MACHINE}" = "dm500hdv2" -o "${MACHINE}" = "dm7020hd" -o "${MACHINE}" = "dm800sev2" -0 "${MACHINE}" = "dm800" -o "${MACHINE}" = "dm8000"]; then
        DRIVERSDATE="20131228"
    else
        DRIVERSDATE='N/A'
    fi
}

do_install_append() {
    install -d ${D}/usr/share/enigma2
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/images/boxes
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static
    install -m 0644 ${S}/BoxBranding/boxes/${MACHINEBUILD}.jpg ${D}/usr/share/enigma2/${MACHINEBUILD}.jpg
    ln ${D}/usr/share/enigma2/${MACHINEBUILD}.jpg ${D}/usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/images/boxes/${MACHINEBUILD}.jpg
}

pkg_postinst_${PN}() {
    if [ -L /usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static/remotes ] ; then
        rm -f /usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static/remotes
    fi
    ln -s /usr/share/enigma2/rc_models /usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static/remotes
}

pkg_postrm_${PN} () {
    if [ -L /usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static/remotes ] ; then
        rm -f /usr/lib/enigma2/python/Plugins/Extensions/OpenWebif/public/static/remotes
    fi
}

FILES_${PN}-src = "/usr/lib/enigma2/python/Components/*.py"
FILES_${PN} = "/usr/lib/enigma2/python/*.so /usr/share /usr/lib/enigma2/python/Components/*.pyo /usr/lib/enigma2/python/Plugins"
FILES_${PN}-dev += "/usr/lib/enigma2/python/*.la"
FILES_${PN}-staticdev += "/usr/lib/enigma2/python/*.a"
FILES_${PN}-dbg += "/usr/lib/enigma2/python/.debug"

