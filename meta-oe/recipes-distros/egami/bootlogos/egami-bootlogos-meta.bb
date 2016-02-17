SUMMARY = "meta file for enigma2 bootlogo packages"

require conf/license/license-gplv2.inc

inherit allarch

DEPENDS = "\
    enigma2-plugin-bootlogos-egami-1 \
    enigma2-plugin-bootlogos-egami-2 \
    enigma2-plugin-bootlogos-egami-3 \
    enigma2-plugin-bootlogos-egami-4 \
    enigma2-plugin-bootlogos-egami-5 \
    enigma2-plugin-bootlogos-egami-6 \
    enigma2-plugin-bootlogos-egami-7 \
    "

PR = "r2"
