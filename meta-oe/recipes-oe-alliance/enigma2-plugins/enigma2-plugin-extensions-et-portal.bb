SUMMARY = "enigma2-plugin-extensions-et-portal"
MAINTAINER = "original by bla666 and inofficial mod by mogli123 and icewaere and pcd and koivo"
SECTION = "extra"
PRIORITY = "optional"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv gettext autotools-brokensep ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "inofficial-3.2.+git${SRCPV}"
PKGV = "inofficial-3.2.+git${GITPKGV}"
PR = "r0"


SRC_URI="git://github.com/e2plugins/etportal-inofficial.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-po"
FILES:${PN} = "/usr/lib /tmp"
FILES:${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/EtPortal/*.py"
FILES:${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/EtPortal/locale/*/*/*.po"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

PARALLEL_MAKEINST = ""

pkg_postinst:${PN}() {
#!/bin/sh
if  [ -f /usr/lib/enigma2/python/Plugins/Extensions/EtPortal/adultpassword ] ; then
     echo ""
else
     mv -f /tmp/adultpassword /usr/lib/enigma2/python/Plugins/Extensions/EtPortal/adultpassword
fi
echo ""
echo "EtPortal successfully installed!"
echo ""
echo ""
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -r /usr/lib/enigma2/python/Plugins/Extensions/EtPortal
echo " EtPortal removed! You should restart enigma2 now!"
exit 0
}
