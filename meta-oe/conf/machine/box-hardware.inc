
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
HAVE_HDMI = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI', 'true' , 'false', d)}"
HAVE_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'YUV', 'true' , 'false', d)}"
HAVE_RCA = "${@bb.utils.contains('MACHINE_FEATURES', 'RCA', 'true' , 'false', d)}"
HAVE_AV_JACK = "${@bb.utils.contains('MACHINE_FEATURES', 'AV_JACK', 'true' , 'false', d)}"
HAVE_SCART = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART', 'true' , 'false', d)}"
HAVE_SCART_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART-YUV', 'true' , 'false', d)}"
HAVE_DVI = "${@bb.utils.contains('MACHINE_FEATURES', 'DVI', 'true' , 'false', d)}"

#Extra Features
HAVE_MINITV = "${@bb.utils.contains('MACHINE_FEATURES', 'MINITV', 'true' , 'false', d)}"
HAVE_HDMI_IN_HD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-HD', 'true' , 'false', d)}"
HAVE_HDMI_IN_FHD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-FHD', 'true' , 'false', d)}"
HAVE_WOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WOL', 'true' , 'false', d)}"
HAVE_WWOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WWOL', 'true' , 'false', d)}"
HAVE_CI = "${@bb.utils.contains('MACHINE_FEATURES', 'ci', 'true' , 'false', d)}"

TRANSCODING = "\
${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'transcoding' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'multitranscoding', 'multitranscoding' , '', d)}\
"
