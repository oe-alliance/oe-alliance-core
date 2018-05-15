SUMMARY = "Archive or Backup your Movielist automatically. MovieArchiver by svox"
MAINTAINER = "svox"
SECTION = "extra"
PRIORITY = "optional"

require conf/license/license-gplv2.inc

inherit gitpkgv gettext
SRCREV = "${AUTOREV}"
PV = "0.2.+git${SRCPV}"
PKGV = "0.2.+git${GITPKGV}"
PR = "r2"


SRC_URI="git://github.com/MovieArchiver/enigma2-plugin-extensions-moviearchiver.git"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc ${libdir}"
FILES_${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver/*.py"
FILES_${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver/locale/*/*/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"


pkg_postinst_${PN}() {
#!/bin/sh
echo "                                                    "
echo "                                                    "
echo "	             MovieArchiver                        "
echo "                    by                              "
echo "   		         svox                             "
echo "                                                    "
echo "                                                    "
echo "        Plugin successfully installed!              "
echo "                                                    "
echo "          Please restart enigma2 now.		          "
echo "                                                    "
echo "                                                    "
echo "                                                    "
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver
echo "                                                          "
echo "        ...MovieArchiver successful removed.              "
echo "             Please restart enigma2 now.		            "
exit 0
}

pkg_prerm_${PN}() {
#!/bin/sh
echo "                                                          "
echo "                                                          "
echo "        ...MovieArchiver successful removed.              "
echo "             Please restart enigma2 now.		            "
echo "                                                          "
echo "                                                          "
}

pkg_preinst_${PN}() {
#!/bin/sh
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/MovieArchiver
echo "                                                    "
echo "	             MovieArchiver                        "
echo "                    by                              "
echo "   		         svox                             "
echo "                                                    "
echo "                                                    "
echo "        Plugin successfully installed!              "
echo "                                                    "
exit 0
}
