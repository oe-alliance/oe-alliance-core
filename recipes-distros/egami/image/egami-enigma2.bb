SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r38"

inherit packagegroup

DEPENDS = "egami-feeds"

RDEPENDS_${PN} = "\
    enigma2-skindefault \
    egami-version-info \
    egami-base-files \
    \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-inimytube \    
    \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-softwaremanager \
    \
    enigma2-plugin-extensions-egamipermanentclock \
    \
    ${@base_contains("MACHINE_BRAND", "UNiBOX", "${BASE_PLUGINS}" , "", d)} \
    ${@base_contains("MACHINE_BRAND", "GI", "${BASE_PLUGINS}" , "", d)} \
    ${@base_contains("MACHINE_BRAND", "Miraclebox", "${BASE_PLUGINS}" , "", d)} \
    ${@base_contains("MACHINE_BRAND", "Atemio", "${BASE_PLUGINS}" , "", d)} \
    ${@base_contains("MACHINE_BRAND", "Sezam", "${SEZAM_PLUGINS}" , "", d)} \
    "
    
BASE_PLUGINS = "\
    enigma2-plugin-extensions-egamifaq \
    enigma2-plugin-extensions-accuweather \
    enigma2-plugin-extensions-mediaportal \    
"

SEZAM_PLUGINS = "\
    enigma2-plugin-systemplugins-3gmodemmanager \
    enigma2-plugin-systemplugins-fastscan \
"