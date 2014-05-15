SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

inherit allarch

DEPENDS = "\
    enigma2-plugin-bootlogos-openvix-v3 \
    enigma2-plugin-bootlogos-openvix-zeus \
    enigma2-plugin-bootlogos-openvix-helios2 \
    enigma2-plugin-bootlogos-openvix-helios3 \
    enigma2-plugin-bootlogos-maxwells-abstract \
    enigma2-plugin-bootlogos-maxwells-Aurora \
    enigma2-plugin-bootlogos-maxwells-Blaze \
    enigma2-plugin-bootlogos-maxwells-Constellation \
    enigma2-plugin-bootlogos-maxwells-curves \
    enigma2-plugin-bootlogos-maxwells-dark3d \
    enigma2-plugin-bootlogos-maxwells-Earth \
    enigma2-plugin-bootlogos-maxwells-FireStick \
    enigma2-plugin-bootlogos-maxwells-Iceberg \
    enigma2-plugin-bootlogos-maxwells-leaves \
    enigma2-plugin-bootlogos-maxwells-PurpleWave \
    enigma2-plugin-bootlogos-maxwells-Ribbons \
    enigma2-plugin-bootlogos-maxwells-River \
    enigma2-plugin-bootlogos-maxwells-Smoke \
    enigma2-plugin-bootlogos-maxwells-sound \
    enigma2-plugin-bootlogos-maxwells-spectrum \
    enigma2-plugin-bootlogos-maxwells-variation \
    "

PR = "r3"
