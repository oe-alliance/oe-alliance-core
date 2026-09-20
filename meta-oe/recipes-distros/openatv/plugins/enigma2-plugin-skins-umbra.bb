SUMMARY = "Umbra skin for OpenATV"
DESCRIPTION = "Native HD, FHD and WQHD skin with configurable style packs and optional e2MDB artwork."
HOMEPAGE = "https://github.com/openatv/UMBRA"
MAINTAINER = "OpenATV Team"
SECTION = "skins"
LICENSE = "GPL-2.0-or-later AND Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSES/GPL-2.0-or-later.txt;md5=eb723b61539feef013de476e68b5c50a \
    file://LICENSES/Apache-2.0.txt;md5=175792518e4ac015ab6696d16c4f607e \
"


require conf/python/python3-compileall.inc
inherit gitpkgv gettext
DEPENDS += "gettext-native"

SRCREV = "${AUTOREV}"
PV = "0.4.25+git"
PKGV = "0.4.25+git${GITPKGV}"
UMBRA_URI ?= "git://github.com/openatv/UMBRA.git;branch=main;protocol=https"
SRC_URI = "${UMBRA_URI}"

RDEPENDS:${PN} += " \
    enigma2-plugin-extensions-oaweather \
    python3-json \
    python3-xml \
"

SKIN_DIR = "${datadir}/enigma2/Umbra"
PLUGIN_DIR = "${libdir}/enigma2/python/Plugins/Extensions/UmbraSettings"
CONVERTER = "${libdir}/enigma2/python/Components/Converter/UmbraEcmInfo"
FILES:${PN} = "${SKIN_DIR} ${PLUGIN_DIR} ${CONVERTER}.pyc"
FILES:${PN}-src = "${PLUGIN_DIR}/*.py ${CONVERTER}.py"
FILES:${PN}-doc += "${datadir}/doc/enigma2-plugin-skins-umbra"

do_configure[noexec] = "1"

do_compile() {
    for po in "${S}"/locale/*.po; do
        [ -f "$po" ] || continue
        language=$(basename "$po" .po)
        locale_dir="${S}/usr/lib/enigma2/python/Plugins/Extensions/UmbraSettings/locale/$language/LC_MESSAGES"
        install -d "$locale_dir"
        msgfmt --check -o "$locale_dir/Umbra.mo" "$po"
    done
}

do_install() {
    install -d ${D}${libdir} ${D}${datadir}
    cp -r --no-preserve=ownership ${S}/usr/lib/. ${D}${libdir}/
    cp -r --no-preserve=ownership ${S}/usr/share/. ${D}${datadir}/
    chmod -R a+rX ${D}${libdir}/enigma2 ${D}${datadir}/enigma2

    for resolution in HD FHD WQHD; do
        for asset in fonts spinner picon_default.png; do
            ln -s ../$asset ${D}${SKIN_DIR}/$resolution/$asset
        done
    done
}

# Reapply only saved Umbra values. Do not change the active skin or restart E2.
# The -src split can leave only legacy bytecode in the runtime package.
pkg_postinst:${PN}() {
    [ -n "$D" ] && exit 0
    [ -f /etc/enigma2/settings ] || exit 0
    script=${libdir}/enigma2/python/Plugins/Extensions/UmbraSettings/reapply
    if [ -f "$script.py" ]; then
        python3 "$script.py"
    else
        python3 "$script.pyc"
    fi
}
