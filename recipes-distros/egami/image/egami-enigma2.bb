SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r36"

inherit packagegroup

DEPENDS = "egami-feeds"

RRECOMMENDS_${PN} = " \
    egami-version-info \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-inimytube \    
    \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-softwaremanager \
    \
    egami-base-files \
    enigma2-plugin-extensions-egamipermanentclock \
    enigma2-plugin-extensions-egamifaq \
    enigma2-plugin-skins-egmega32 \
    enigma2-plugin-extensions-accuweather \
    \
    ${@base_contains("MACHINEBUILD", "sezam1000hd", "enigma2-plugin-systemplugins-fastscan" , "", d)} \
    ${@base_contains("MACHINEBUILD", "sezam5000hd", "enigma2-plugin-systemplugins-fastscan" , "", d)} \
    ${@base_contains("MACHINEBUILD", "sezammarvel", "enigma2-plugin-systemplugins-fastscan" , "", d)} \
    \
    ${@base_contains("MACHINEBUILD", "sezam1000hd", "enigma2-plugin-systemplugins-3gmodemmanager" , "", d)} \
    ${@base_contains("MACHINEBUILD", "sezam5000hd", "enigma2-plugin-systemplugins-3gmodemmanager" , "", d)} \
    ${@base_contains("MACHINEBUILD", "sezammarvel", "enigma2-plugin-systemplugins-3gmodemmanager" , "", d)} \    
    "
