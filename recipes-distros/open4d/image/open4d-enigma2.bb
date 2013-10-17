DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r3"

inherit task

RCONFLICTS_ = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "open4d-feeds"

#make sure default skin is installed.
RDEPENDS_${PN} += "${E2DEFAULTSKIN} "

RRECOMMENDS = "\
	open4d-version-info \
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-systemplugins-videomode \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-systemplugins-videoenhancement \
"

