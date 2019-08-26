SUMMARY = "KravenVB Skin for Enigma2 by Kraven Team"
MAINTAINER = "Kraven Team"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch gettext

SRCREV = "${AUTOREV}"
PV = "6.6+git${SRCPV}"
PKGV = "6.6+git${GITPKGV}"
VER="6.6"

DEPENDS_${PN} += "gettext-native"
RDEPENDS_${PN} += "python-imaging python-subprocess python-requests python-lxml enigma2-plugin-systemplugins-mphelp"

SRC_URI="git://github.com/KravenHD/KravenVB.git;protocol=git"

PACKAGES =+ " ${PN}-src"

FILES_${PN} = "/usr/*"
FILES_${PN}-src = "\
    ${libdir}/enigma2/python/Components/Converter/*.py \
    ${libdir}/enigma2/python/Components/Renderer/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/*/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/*/*/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/locale/*/LC_MESSAGES/*.po \
    "

S = "${WORKDIR}/git"

do_compile() {
    python -O -m compileall ${S}/usr
    for f in $(find ${S}/locale -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
        mkdir -p ${S}${libdir}/enigma2/python/Plugins/Extensions/KravenVB/locale/${l%}/LC_MESSAGES
        msgfmt -o ${S}${libdir}/enigma2/python/Plugins/Extensions/KravenVB/locale/${l%}/LC_MESSAGES/KravenVB.mo ${S}${libdir}/enigma2/python/Plugins/Extensions/KravenVB/locale/$l.po
    done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -r --preserve=mode,links ${S}${libdir}/* ${D}${libdir}/
    cp -r --preserve=mode,links ${S}${datadir}/* ${D}${datadir}/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f /tmp/kravenskin ]; then
    mv -f /tmp/kravenskin ${datadir}/enigma2/KravenVB/skin.xml
fi
if [ -d /tmp/graphics ]; then
    mv -f /tmp/graphics/*.* ${datadir}/enigma2/KravenVB/graphics
fi
if [ -f /tmp/skin-user.xml ]; then
    mv -f /tmp/skin-user.xml ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/data
fi
if [ -f ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/plugin.py* ]; then
    wget -q -O /dev/null 'http://127.0.0.1/web/message?type=1&text=KravenVB%20wurde%20erfolgreich%20installiert.%0A%0AZur%20Nutzung%20rufen%20Sie%20das%20Plugin%20auf,%20speichern%20Ihre%20Einstellungen%0Aund%20starten%20die%20Oberfl%C3%A4che%20neu.&timeout=13' || true
fi
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##..########..                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..########..                 "
echo "                    ..##...##...##.....##.                 "
echo "                    ...##.##....##.....##.                 "
echo "                    ....###.....########..                 "
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf ${datadir}/enigma2/KravenVB
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/KravenVB
rm -rf ${libdir}/enigma2/python/Components/Converter/KravenVB*
rm -rf ${libdir}/enigma2/python/Components/Renderer/KravenVB*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##..########..                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..########..                 "
echo "                    ..##...##...##.....##.                 "
echo "                    ...##.##....##.....##.                 "
echo "                    ....###.....########..                 "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "KravenVB Skin will be now installed..."
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenVB is now being removed...          "
echo "                                                           "
if [ -e ${datadir}/enigma2/KravenVB/skin.xml ]; then
    cp ${datadir}/enigma2/KravenVB/skin.xml /tmp/kravenskin
fi
if [ -d ${datadir}/enigma2/KravenVB/graphics ]; then
    mkdir /tmp/graphics
    cp ${datadir}/enigma2/KravenVB/graphics/* /tmp/graphics/
fi
if [ -f ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/data/skin-user.xml ]; then
    cp ${libdir}/enigma2/python/Plugins/Extensions/KravenVB/data/skin-user.xml /tmp/
fi
if [ -f ${datadir}/enigma2/KravenVB/skin.xml ]; then
    rm -rf ${datadir}/enigma2/KravenVB/skin.xml
fi
exit 0
}

do_package_qa[noexec] = "1"
