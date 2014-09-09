SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "2.3"
PR = "r9"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "openvixhd-feeds"

RDEPENDS_${PN} = "\
    enigma2-skindefault \
    enigma2-plugin-vix-core \
    "

RRECOMMENDS_${PN} = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-systemplugins-crossepg \
    "

