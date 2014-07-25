SUMMARY = "GigaBlue IPBox Server"
MAINTAINER = "GigaBlue"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI="git://github.com/openmips/gbremote-server.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc /usr"

pkg_postinst_${PN}() {
#!/bin/sh
chmod +x /etc/init.d/gbipbox_server
chmod +x /usr/bin/gbipbox_server
update-rc.d gbipbox_server defaults
echo "                                                    "
echo "              GigaBlue IPBox Server                 "
echo "                                                    "
echo "       GBIPBox Server successfully installed!       "
echo "                                                    "
echo "                 Please reboot!                     "
echo "                                                    "
sleep 10
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
update-rc.d gbipbox_server remove
echo "                                                    "
echo "              GigaBlue IPBox Server                 "
echo "                                                    "
echo "       GBIPBox Server successfully removed!         "
echo "                                                    "
echo "                 Please reboot!                     "
echo "                                                    "
sleep 10
exit 0
}

do_install() {
    cp -rp ${S}/etc ${D}/
    cp -rp ${S}/usr ${D}/
}
