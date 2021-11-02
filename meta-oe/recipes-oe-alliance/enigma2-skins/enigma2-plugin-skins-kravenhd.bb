SUMMARY = "KravenHD Skin for Enigma2 by Team Kraven"
MAINTAINER = "Team Kraven"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch gettext

SRCREV = "${AUTOREV}"
PV = "7.8.x+git${SRCPV}"
PKGV = "7.8.x+git${GITPKGV}"
VER = "7.8.x"

DEPENDS += "gettext-native"
RDEPENDS_${PN} += "python-imaging python-subprocess python-requests python-lxml enigma2-plugin-systemplugins-mphelp"
RCONFLICTS_${PN} += "enigma2-plugin-skins-kravenfhd enigma2-plugin-skins-kravenvb"
RREPLACES_${PN} += "enigma2-plugin-skins-kravenfhd enigma2-plugin-skins-kravenvb"
RPROVIDES_${PN} += "enigma2-plugin-skins-kravenfhd enigma2-plugin-skins-kravenvb"

SRC_URI = "git://github.com/oerlgrey/KravenHD.git;protocol=https"

FILES_${PN} = "/usr/*"
FILES_${PN}-src = "\
    /usr/lib/enigma2/python/Components/Converter/*.py \
    /usr/lib/enigma2/python/Components/Renderer/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/*/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/*/*/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/locale/*/LC_MESSAGES/*.po \
    "

S = "${WORKDIR}/git"

do_compile() {
    python -O -m compileall ${S}/usr
    for f in $(find ${S}/locale -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
        #mkdir -p ${S}/usr/lib/enigma2/python/Plugins/Extensions/KravenHD/locale/${l%}/LC_MESSAGES
        #msgfmt -o ${S}/usr/lib/enigma2/python/Plugins/Extensions/KravenHD/locale/${l%}/LC_MESSAGES/KravenHD.mo ${S}/usr/lib/enigma2$
    done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}/usr/lib/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f /tmp/kravenhdskin ]; then
    mv -f /tmp/kravenhdskin /usr/share/enigma2/KravenHD/skin.xml
fi
if [ -d /tmp/graphicshd ]; then
    mv -f /tmp/graphicshd/* /usr/share/enigma2/KravenHD/graphics/
fi
if [ -f /tmp/skin-user-hd.xml ]; then
    mv -f /tmp/skin-user-hd.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/HD/skin-user.xml
fi
if [ -f /tmp/skin-user-fhd.xml ]; then
    mv -f /tmp/skin-user-fhd.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/FHD/skin-user.xml
fi
if [ -f /tmp/icon1.png ]; then
    mv -f /tmp/icon1.png /usr/share/enigma2/KravenHD/buttons/icon1.png
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/plugin.py* ]; then
    wget -q -O /dev/null 'http://127.0.0.1/web/message?type=1&text=KravenHD%20wurde%20erfolgreich%20installiert.%0A%0AZur%20Nutzung%20rufen%20Sie%20das%20Plugin%20auf,%20speichern%20Ihre%20Einstellungen%0Aund%20starten%20die%20Oberfl%C3%A4che%20neu.&timeout=13' || true
fi
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##....##..#######...                  "
echo "                    .##....##..##....##..                  "
echo "                    .##....##..##.....##.                  "
echo "                    .########..##.....##.                  "
echo "                    .##....##..##.....##.                  "
echo "                    .##....##..##....##..                  "
echo "                    .##....##..#######...                  "
echo "                                                           "
echo "               ...Skin successful installed.               "
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
echo "                    .##....##..#######...                  "
echo "                    .##....##..##....##..                  "
echo "                    .##....##..##.....##.                  "
echo "                    .########..##.....##.                  "
echo "                    .##....##..##.....##.                  "
echo "                    .##....##..##....##..                  "
echo "                    .##....##..#######...                  "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenHD is now being installed...        "
echo "                                                           "
exit 0                                                           
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenHD is now being removed...          "
echo "                                                           "
if [ -e /usr/share/enigma2/KravenHD/skin.xml ]; then
    cp /usr/share/enigma2/KravenHD/skin.xml /tmp/kravenhdskin
fi
if [ -d /usr/share/enigma2/KravenHD/graphics ]; then
    mkdir /tmp/graphicshd
    cp /usr/share/enigma2/KravenHD/graphics/* /tmp/graphicshd/
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/HD/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/HD/skin-user.xml /tmp/skin-user-hd.xml
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/FHD/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenHD/data/FHD/skin-user.xml /tmp/skin-user-fhd.xml
fi
if [ -f /usr/share/enigma2/KravenHD/buttons/icon1.png ]; then
    cp /usr/share/enigma2/KravenHD/buttons/icon1.png /tmp/icon1.png
fi
if [ -f /usr/share/enigma2/KravenHD/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenHD/skin.xml
fi
exit 0
}

do_package_qa[noexec] = "1"
