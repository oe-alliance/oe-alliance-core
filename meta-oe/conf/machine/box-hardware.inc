
# Hardware Branding

#Display type
DISPLAY_TYPE = "\
${@bb.utils.contains('MACHINE_FEATURES', 'textlcd', 'textlcd' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', '7segment', '7segment' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd96', 'bwlcd96' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd128', 'bwlcd128' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd140', 'bwlcd140' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd255', 'bwlcd255' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd', 'colorlcd' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd220', 'colorlcd220' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd128', 'colorlcd128' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd400', 'colorlcd400' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd390', 'colorlcd390' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd480', 'colorlcd480' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd720', 'colorlcd720' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd800', 'colorlcd800' , '', d)}\
"

# Connectors
HAVE_HDMI = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI', 'True' , 'False', d)}"
HAVE_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'YUV', 'True' , 'False', d)}"
HAVE_RCA = "${@bb.utils.contains('MACHINE_FEATURES', 'RCA', 'True' , 'False', d)}"
HAVE_AV_JACK = "${@bb.utils.contains('MACHINE_FEATURES', 'AV_JACK', 'True' , 'False', d)}"
HAVE_SCART = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART', 'True' , 'False', d)}"
HAVE_SCART_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART-YUV', 'True' , 'False', d)}"
HAVE_DVI = "${@bb.utils.contains('MACHINE_FEATURES', 'DVI', 'True' , 'False', d)}"
HAVE_SVIDEO = "${@bb.utils.contains("MACHINE_FEATURES", "SVIDEO", "True", "False", d)}"

#Extra Features
HAVE_MINITV = "${@bb.utils.contains('MACHINE_FEATURES', 'MINITV', 'True' , 'False', d)}"
HAVE_HDMI_IN_HD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-HD', 'True' , 'False', d)}"
HAVE_HDMI_IN_FHD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-FHD', 'True' , 'False', d)}"
HAVE_WOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WOL', 'True' , 'False', d)}"
HAVE_WWOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WWOL', 'True' , 'False', d)}"
HAVE_CI = "${@bb.utils.contains('MACHINE_FEATURES', 'ci', 'True' , 'False', d)}"
HAVE_TRANSCODING = "${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'True' , 'False', d)}"
HAVE_MULTITRANSCODING = "${@bb.utils.contains('MACHINE_FEATURES', 'multitranscoding', 'True' , 'False', d)}"
HAVE_FHDSKIN = "${@bb.utils.contains('MACHINE_FEATURES', 'skins1080', 'True' , 'False', d)}"
HAVE_SMALLFLASH = "${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "True", "False", d)}"
HAVE_MIDDLEFLASH = "${@bb.utils.contains("MACHINE_FEATURES", "middleflash", "True", "False", d)}"
HAVE_MULTILIB = "${@bb.utils.contains("MACHINE_FEATURES", "multilib", "True", "False", d)}"
HAVE_VFDSYMBOL = "${@bb.utils.contains("MACHINE_FEATURES", "vfdsymbol", "True", "False", d)}"

RCTYPE ??= "0"
RCNAME ??= "dmm1"
RCIDNUM ??= "2"

#deprecated setting
TRANSCODING = "\
${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'transcoding' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'multitranscoding', 'multitranscoding' , '', d)}\
"

LANGUAGECHECK = "${@bb.utils.contains_any("FLASHSIZE", "64 96", "DEU;ENG" , "multilang", d)}"
LANGUAGE = "${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash BootloaderMax110", "DEU;ENG", "${LANGUAGECHECK}", d)}"

STB_PLATFORM ?= "${MACHINE}"
MEDIASERVICE ?= "${@bb.utils.contains("MACHINE_FEATURES", "himedia", "servicehisilicon" , "servicegstreamer", d)}"
BLINDSCAN_BINARY ?= "blindscan"
FORCE ?= "no"
SUPPORT_DBOXLCD ?= "${@bb.utils.contains_any("MACHINE_FEATURES", "textlcd", "True", "False", d)}"
DEVELOPER_NAME ?= "${DISTRO_NAME}"
FRIENDLY_FAMILY ?= "${MACHINE} ${BUILDMACHINE}"