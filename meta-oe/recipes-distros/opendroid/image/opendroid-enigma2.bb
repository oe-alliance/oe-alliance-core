SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r48"

inherit packagegroup



RRECOMMENDS_${PN} = "\
    enigma2-plugin-settings-default-opendroid \
    enigma2-plugin-extensions-addonopendroid \
    enigma2-plugin-extensions-autosettings \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videoenhancement \
    enigma2-plugin-extensions-volume-adjust \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \ 
   "
