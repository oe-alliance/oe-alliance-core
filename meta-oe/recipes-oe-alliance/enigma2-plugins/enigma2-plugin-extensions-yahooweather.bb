DESCRIPTION = "enigma2-plugin-extensions-yahooweather"
MAINTAINER = "original by m43c0 and mmark and mod by mogli123"
SECTION = "extra"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

inherit gitpkgv ${PYTHON_PN}native autotools-brokensep gettext

SRCREV = "${AUTOREV}"
PV = "1.2.+git${SRCPV}"
PKGV = "1.2.+git${GITPKGV}"
PR = "r0"

SRC_URI="git://github.com/oe-alliance/YahooWeather.git"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

do_configure_prepend() {
sed -i 's/python/python2/g' ${S}/xml2po.py
}


PARALLEL_MAKEINST = ""

CONFFILES_${PN} = " \
    ${libdir}/enigma2/python/Plugins/Extensions/YahooWeather/Config/Location_id \
    ${libdir}/enigma2/python/Plugins/Extensions/YahooWeather/Config/Region_id "

PACKAGES =+ "${PN}-po"
FILES_${PN} = "${libdir}"
FILES_${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/YahooWeather/*.py"
FILES_${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/YahooWeather/locale/*/*/*.po"

pkg_postinst_${PN}() {
#!/bin/sh
echo ""
echo "YahooWeather successfully installed!"
echo ""
echo ""
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -r /usr/lib/enigma2/python/Plugins/Extensions/YahooWeather
echo " YahooWeather removed! You should restart enigma2 now!"
exit 0
}
