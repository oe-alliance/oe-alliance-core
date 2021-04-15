SUMMARY = "Internet SpeedTest by madhouse"
MAINTAINER = "madhouse"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

SRC_URI="git://github.com/m4dhouse/Internet-Speedtest.git;protocol=git;branch=main"

FILES_${PN}-src = "\
    ${libdir}/enigma2/python/Components/Converter/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/InternetSpeedTest/*.py \
    "

S = "${WORKDIR}/git/Internet-Speedtest"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "*****************************************"
echo "*      Internet SpeedTest Installed     *"
echo "*****************************************"
sleep 2
echo $LINE
echo "*****************************************"
echo "*            Restart Enigma2            *"
echo "*****************************************"
sleep 2
killall -9 enigma2
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/InternetSpeedTest > /dev/null 2>&1
rm -rf /usr/lib/enigma2/python/Components/Converter/SP* > /dev/null 2>&1
echo "*****************************************"
echo "*    Restart GUI to finish uninstall!   *"
echo "*****************************************"
sleep 6
exit 0                                                         "
}

pkg_preinst_${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/InternetSpeedTest > /dev/null 2>&1
rm -rf /usr/lib/enigma2/python/Components/Converter/SP* > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
