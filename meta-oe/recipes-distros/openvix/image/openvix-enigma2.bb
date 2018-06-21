SUMMARY = "OpenViX Enigma2"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r19"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "openvix-feeds"

RDEPENDS_${PN} = "\
    enigma2-skindefault \
    openvix-core \
    "

RRECOMMENDS_${PN} = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-skins-openvix-magic-fhd \
    enigma2-plugin-skins-openvix-simple-ten-eighty \	
    enigma2-plugin-skins-openvix-vix-day-hd \
    enigma2-plugin-skins-openvix-youvix-blue \
    enigma2-plugin-systemplugins-crossepg \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    "

RRECOMMENDS_${PN}_append_et8500 = " enigma2-plugin-extensions-yahooweather"
RRECOMMENDS_${PN}_append_formuler4turbo = " enigma2-plugin-systemplugins-vfdcontrol"
RRECOMMENDS_${PN}_append_osmini = " enigma2-plugin-systemplugins-vfdcontrol"
RRECOMMENDS_${PN}_append_osnino = " enigma2-plugin-systemplugins-vfdcontrol"
RRECOMMENDS_${PN}_append_osninoplus = " enigma2-plugin-systemplugins-vfdcontrol"
RRECOMMENDS_${PN}_append_tmnanoseplus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS_${PN}_append_tmnanosem2 = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS_${PN}_append_tmnanosem2plus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS_${PN}_append_tmtwin4k = " enigma2-plugin-systemplugins-tempfancontrol"
