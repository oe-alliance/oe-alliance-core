SUMMARY = "BundesligaFHD skin for openATV images"
DESCRIPTION = "Full HD Bundesliga skin and configuration plugin for openATV images."
HOMEPAGE = "https://github.com/stein17/Skins-for-openATV"
MAINTAINER = "stein17"
SECTION = "skins"

require conf/license/license-gplv2.inc

DEPENDS = "gettext-native"

require conf/python/python3-compileall.inc

inherit allarch gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.4+git"
PKGV = "1.4+git${GITPKGV}"

SRC_URI = "git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"
S = "${UNPACKDIR}/${BP}/BundesligaFHD"

RDEPENDS:${PN} += "enigma2-plugin-extensions-oaweather"

SKIN_DIR = "${datadir}/enigma2/BundesligaFHD"
PLUGIN_DIR = "${libdir}/enigma2/python/Plugins/Extensions/BundesligaFHD"
TRANSLATION_DOMAIN = "BundesligaFHD"

FILES:${PN} = " \
    ${SKIN_DIR} \
    ${PLUGIN_DIR} \
    ${libdir}/enigma2/python/Components/Converter/BLFHD*.pyc \
    ${libdir}/enigma2/python/Components/Renderer/BLFHD*.pyc \
"

FILES:${PN}-src = " \
    ${PLUGIN_DIR}/*.py \
    ${libdir}/enigma2/python/Components/Converter/BLFHD*.py \
    ${libdir}/enigma2/python/Components/Renderer/BLFHD*.py \
"

do_compile[cleandirs] += "${B}/locale"

do_compile() {
	for translation in ${S}/po/*.po ${S}${PLUGIN_DIR}/locale/*.po; do
		[ -f "$translation" ] || continue
		language=$(basename "$translation" .po)
		install -d ${B}/locale/$language/LC_MESSAGES
		msgfmt -o ${B}/locale/$language/LC_MESSAGES/${TRANSLATION_DOMAIN}.mo "$translation"
	done
}

do_install() {
	install -d ${D}${prefix}
	cp -r --no-preserve=ownership ${S}${prefix}/. ${D}${prefix}/
	chmod -R a+rX ${D}${libdir}/enigma2 ${D}${datadir}/enigma2

	if [ -d ${D}${PLUGIN_DIR}/locale ]; then
		find ${D}${PLUGIN_DIR}/locale -type f \( -name '*.po' -o -name '*.pot' \) -delete
	fi
	if [ -d ${B}/locale ]; then
		install -d ${D}${PLUGIN_DIR}/locale
		cp -r --no-preserve=ownership ${B}/locale/. ${D}${PLUGIN_DIR}/locale/
	fi
}
