SUMMARY = "enigma2-plugin-extensions-et-portal"
MAINTAINER = "original by bla666 and inofficial mod by mogli123 and icewaere and pcd and koivo"
SECTION = "extra"
PRIORITY = "optional"

require conf/license/license-gplv2.inc

inherit gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "inofficial-3.2.+git${SRCPV}"
PKGV = "inofficial-3.2.+git${GITPKGV}"
PR = "r2"


SRC_URI="git://github.com/e2plugins/etportal-inofficial.git"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/usr/lib /tmp"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/EtPortal/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/EtPortal/locale/*/*/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    --with-libsdl=no --with-boxtype=${MACHINE} --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

pkg_postinst_${PN}() {
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

pkg_postrm_${PN}() {
#!/bin/sh
rm -r /usr/lib/enigma2/python/Plugins/Extensions/EtPortal
echo " EtPortal removed! You should restart enigma2 now!"
exit 0
}
