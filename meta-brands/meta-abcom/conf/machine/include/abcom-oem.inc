
MACHINE_FEATURES += " hisil mali emmc skins1080 multitranscoding kodi18 himedia no-subssupport blindscan-dvbs gfwol ci fastboot mountboot \
${@bb.utils.contains('MACHINEBUILD', 'abpulse4k', ' 7segment dvb-c' , '', d)}\
"

BRAND_OEM = "abcom"
MACHINE_BRAND = "AB-COM"
IMAGEDIR = "${MACHINE}"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'abpulse4k', 'PULSe 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'abpulse4kmini', 'PULSe 4K MINI' , '', d)}\
"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
