SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

inherit allarch

DEPENDS = "\
    enigma2-plugin-bootlogos-openvix-v3 \
    enigma2-plugin-bootlogos-openvix-zeus \
    enigma2-plugin-bootlogos-openvix-helios2 \
    enigma2-plugin-bootlogos-openvix-helios3 \
    enigma2-plugin-bootlogos-maxwells-abstract \
    enigma2-plugin-bootlogos-maxwells-aurora \
    enigma2-plugin-bootlogos-maxwells-blaze \
    enigma2-plugin-bootlogos-maxwells-constellation \
    enigma2-plugin-bootlogos-maxwells-curves \
    enigma2-plugin-bootlogos-maxwells-dark3d \
    enigma2-plugin-bootlogos-maxwells-earth \
    enigma2-plugin-bootlogos-maxwells-firestick \
    enigma2-plugin-bootlogos-maxwells-iceberg \
    enigma2-plugin-bootlogos-maxwells-leaves \
    enigma2-plugin-bootlogos-maxwells-purplewave \
    enigma2-plugin-bootlogos-maxwells-ribbons \
    enigma2-plugin-bootlogos-maxwells-river \
    enigma2-plugin-bootlogos-maxwells-smoke \
    enigma2-plugin-bootlogos-maxwells-sound \
    enigma2-plugin-bootlogos-maxwells-spectrum \
    enigma2-plugin-bootlogos-maxwells-variation \
    "

PR = "r3"
