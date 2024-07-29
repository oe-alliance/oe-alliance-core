SUMMARY = "Glamour Aura Sky skin for new generation STBs with OpenDroid/OE-A based images"
MAINTAINER = "MCelliotG"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"
PR = "r1"

RRECOMMENDS:${PN} = "enigma2-plugin-extensions-weathermsn enigma2-plugin-extensions-bitrate"

SRC_URI = "git://github.com/formiano/GlamourAuraSky-skin.git;protocol=https;branch=main"

FILES:${PN} = "/usr"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
	install -d ${D}/usr
	cp -r --preserve=mode,links ${S}/usr/* ${D}/usr/
	chmod -R a+rX ${D}/usr
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...GlamourAuraSky Skin Full HD by MCelliotG successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/GlamourAuraSky
rm -rf /usr/lib/enigma2/python/Components/Converter/Glam*
rm -rf /usr/lib/enigma2/python/Components/Renderer/Glam*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
}

pkg_preinst:${PN} () {
#!/bin/sh
echo "                                                                           "
echo "  GlamourAuraSky Skin Full HD by MCelliotG is now being installed...       "
echo "                                                                           "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                                "
echo "Glamour Aura Sky-skin is now being removed from your receiver..."
echo "                                                                "
}

do_package_qa[noexec] = "1"

