SUMMARY = "Jedi EPG Xtream"
DESCRIPTION = "Assign 3rd Party EPG to IPTV Channels"
HOMEPAGE = "https://www.linuxsat-support.com/thread/141140-jedi-epg-xtream/"
MAINTAINER = "kiddac"
LICENSE = "GPLv3"
SECTION = "misc"
PRIORITY = "optional"

inherit allarch gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.15-git${SRCPV}"
PKGV = "1.15-git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/kiddac/Jedi-EPG-XStream.git;protocol=git;branch=main"

SYSCONFDIR = "${sysconfdir}/enigma2/jediepgxtream"
LIBDIR = "${libdir}/enigma2/python/Plugins/Extensions/JediEPGXtream"

FILES_${PN} = "${SYSCONFDIR}/* ${LIBDIR}/*"

PACKAGES = "${PN}"


S = "${WORKDIR}/git/JediEPGXtream"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${SYSCONFDIR}
    install -d ${D}/${LIBDIR}
    cp -rf ${S}/${SYSCONFDIR}/* ${D}/${SYSCONFDIR}/
    cp -rf ${S}/${LIBDIR}/* ${D}/${LIBDIR}/
    rm -rf ${D}/${LIBDIR}/__pycache__
    find ${D} -name *.po -execdir rm -f "{}" "+"
}

pkg_preinst_${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/JediEPGXtream > /dev/null 2>&1
rm -rf /etc/epgimport/*jex*.* > /dev/null 2>&1
}

pkg_postinst_${PN}() {
#!/bin/sh
echo "*********************************************************"
echo "*                                                       *"
echo "* Jedi EPG Xtream                                       *"
echo "* by KiddaC (c) 2020                                    *"
echo "*                                                       *"
echo "* Restart Enigma-2 GUI to add plugin into plugin list   *"
echo "*********************************************************"
sleep 5
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/JediEPGXtream > /dev/null 2>&1
rm -rf /etc/epgimport/*jex*.* > /dev/null 2>&1
echo "Restart GUI to finish uninstall!"
sleep 10
}
