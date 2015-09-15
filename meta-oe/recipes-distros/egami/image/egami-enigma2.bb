SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r9"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "egami-feeds"

RDEPENDS_${PN} = "\
    enigma2-skindefault \
    \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-accuweather \
    enigma2-plugin-extensions-egamipermanentclock \
    enigma2-plugin-extensions-egaminews \
    enigma2-plugin-extensions-egamiboot \
    enigma2-plugin-extensions-egamifaq \
    \
    enigma2-plugin-systemplugins-egamipluginspeedup \
    \
    ${@base_contains("MACHINE_BRAND", "Sezam", "${SEZAM_PLUGINS}" , "", d)} \
"

RRECOMMENDS_${PN} = "\
    enigma2-plugin-extensions-enhancedmoviecenter \
    \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-softwaremanager \
"
SEZAM_PLUGINS = "\
    enigma2-plugin-systemplugins-3gmodemmanager \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-extensions-inimytube python-twisted-web python-gdata python-textutils python-json python-google-api-client python-httplib2 python-youtube-dl-src python-ctypes python-six python-oauth2client python-uritemplate \
"
