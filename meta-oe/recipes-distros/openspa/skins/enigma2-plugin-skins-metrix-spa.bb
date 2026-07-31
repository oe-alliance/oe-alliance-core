SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "http://open-store.net "
SECTION = "base"
PRIORITY = "required"
LICENSE = "LicenseRef-proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv gettext

DEPENDS += "gettext-native"

SRCREV = "${AUTOREV}"

PV = "2.1+git"
PKGV = "2.1+git${GITPKGV}"

VER = "2.1"
PR = "r0"

PACKAGES += " \
    enigma2-plugin-skins-metrix-spa-fhd-icons \
    enigma2-plugin-skins-metrix-spa-uhd-icons \
"

PROVIDES += " \
    enigma2-plugin-skins-metrix-spa-fhd-icons \
    enigma2-plugin-skins-metrix-spa-uhd-icons \
"

RPROVIDES:enigma2-plugin-skins-metrix-spa-fhd-icons += "enigma2-plugin-skins-metrix-spa-fhd-icons"
RPROVIDES:enigma2-plugin-skins-metrix-spa-uhd-icons += "enigma2-plugin-skins-metrix-spa-uhd-icons"

SRC_URI = "git://github.com/openspa/MetrixHD.git;protocol=https;branch=py3"

FILES:enigma2-plugin-skins-metrix-spa-fhd-icons = "/usr/share/enigma2/MetrixHD/FHD"
FILES:enigma2-plugin-skins-metrix-spa-uhd-icons = "/usr/share/enigma2/MetrixHD/UHD"

FILES:${PN} = "${libdir} /usr/share ${sysconfdir}"

do_compile() {
    for f in $(find ${S}/locale -name "*.po"); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
        mkdir -p ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES
        msgfmt -o ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES/MyMetrixLite.mo ${S}/locale/$l.po
    done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    install -d ${D}/etc/enigma2

    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}${libdir}/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
    cp -r --preserve=mode,links ${S}/etc/enigma2/* ${D}/etc/enigma2/
}
