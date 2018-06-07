SUMMARY = "KravenHD Skin for Enigma2 by Kraven"
MAINTAINER = "Kraven"


require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "7.3.0+git${SRCPV}"
PKGV = "7.3.0+git${GITPKGV}"
VER="7.3.0"

RDEPENDS_${PN} = "python-requests python-subprocess python-imaging enigma2-plugin-systemplugins-mphelp python-lxml"

SRC_URI="git://github.com/KravenHD/KravenHD.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f /tmp/ibar.png ]; then
    mv -f /tmp/ibar.png /usr/share/enigma2/KravenHD
fi
if [ -f /tmp/ibaro.png ]; then
    mv -f /tmp/ibaro.png /usr/share/enigma2/KravenHD
fi
if [ -f /tmp/ibaro2.png ]; then
    mv -f /tmp/ibaro2.png /usr/share/enigma2/KravenHD
fi
if [ -f /tmp/ibaro3.png ]; then
    mv -f /tmp/ibaro3.png /usr/share/enigma2/KravenHD
fi
if [ -f /tmp/backg.png ]; then
    mv -f /tmp/backg.png /usr/share/enigma2/KravenHD
fi
if [ -f /tmp/header-kraven/ibar_000000.png ]; then
    mkdir /usr/share/enigma2/KravenHD/header-kraven
    mv -f /tmp/header-kraven/*.* /usr/share/enigma2/KravenHD/header-kraven
fi
if [ -f /tmp/skin-user.xml ]; then
    mv -f /tmp/skin-user.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data
fi
if [ -f /tmp/icons-dark/icons/key_ok.png ]; then
	mkdir /usr/share/enigma2/KravenHD/icons-dark/icons
	mkdir /usr/share/enigma2/KravenHD/icons-dark/infobar
	mkdir /usr/share/enigma2/KravenHD/icons-dark/message
	mv -f /tmp/icons-dark/icons/*.* /usr/share/enigma2/KravenHD/icons-dark/icons
	mv -f /tmp/icons-dark/infobar/*.* /usr/share/enigma2/KravenHD/icons-dark/infobar
	mv -f /tmp/icons-dark/message/*.* /usr/share/enigma2/KravenHD/icons-dark/message
	rm -rf /tmp/icons-dark
	mkdir /usr/share/enigma2/KravenHD/icons-light/icons
	mkdir /usr/share/enigma2/KravenHD/icons-light/infobar
	mkdir /usr/share/enigma2/KravenHD/icons-light/message
	mv -f /tmp/icons-light/icons/*.* /usr/share/enigma2/KravenHD/icons-light/icons
	mv -f /tmp/icons-light/infobar/*.* /usr/share/enigma2/KravenHD/icons-light/infobar
	mv -f /tmp/icons-light/message/*.* /usr/share/enigma2/KravenHD/icons-light/message
	rm -rf /tmp/icons-light
fi
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##.########.                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .#########.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.########.                   "
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenHD
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenHD*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##.########.                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .#########.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.########.                   "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for obsolete KravenHD Plugin"
if [ -f /usr/share/enigma2/KravenHD/ibar.png ]; then
    cp /usr/share/enigma2/KravenHD/ibar.png /tmp
fi
if [ -f /usr/share/enigma2/KravenHD/ibaro.png ]; then
    cp /usr/share/enigma2/KravenHD/ibaro.png /tmp
fi
if [ -f /usr/share/enigma2/KravenHD/ibaro2.png ]; then
    cp /usr/share/enigma2/KravenHD/ibaro2.png /tmp
fi
if [ -f /usr/share/enigma2/KravenHD/ibaro3.png ]; then
    cp /usr/share/enigma2/KravenHD/ibaro3.png /tmp
fi
if [ -f /usr/share/enigma2/KravenHD/backg.png ]; then
    cp /usr/share/enigma2/KravenHD/backg.png /tmp
fi
if [ -f /etc/kraven_profile_1 ]; then
    mv /etc/kraven_profile_* /etc/enigma2
fi
if [ -f /etc/kraven_default_1 ]; then
    cp /etc/kraven_default_* /etc/enigma2
fi
if [ -f /usr/share/enigma2/KravenHD/header-kraven/ibar_000000.png ]; then
    mkdir /tmp/header-kraven
	cp /usr/share/enigma2/KravenHD/header-kraven/*.* /tmp/header-kraven
fi
if [ -f /usr/share/enigma2/KravenHD/icons-dark/icons/key_ok.png ]; then
    mkdir /tmp/icons-dark
    mkdir /tmp/icons-dark/icons
    mkdir /tmp/icons-dark/infobar
    mkdir /tmp/icons-dark/message
	cp /usr/share/enigma2/KravenHD/icons-dark/icons/*.* /tmp/icons-dark/icons
	cp /usr/share/enigma2/KravenHD/icons-dark/infobar/*.* /tmp/icons-dark/infobar
	cp /usr/share/enigma2/KravenHD/icons-dark/message/*.* /tmp/icons-dark/message
    mkdir /tmp/icons-light
    mkdir /tmp/icons-light/icons
    mkdir /tmp/icons-light/infobar
    mkdir /tmp/icons-light/message
	cp /usr/share/enigma2/KravenHD/icons-light/icons/*.* /tmp/icons-light/icons
	cp /usr/share/enigma2/KravenHD/icons-light/infobar/*.* /tmp/icons-light/infobar
	cp /usr/share/enigma2/KravenHD/icons-light/message/*.* /tmp/icons-light/message
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/skin-user.xml /tmp
fi
if [ -f /usr/share/enigma2/KravenHD/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenHD/skin.xml
fi
echo "KravenHD Skin will be now installed..."
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "KravenHD Skin will be now removed..."
exit 0
}

do_package_qa[noexec] = "1"

