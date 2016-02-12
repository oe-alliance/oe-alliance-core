RSUGGESTS_${PN} = ""

PROVIDES =+ " libavcodec53 libavformat53 libav"
PACKAGES =+ " libavcodec53 libavformat53 libav"

DEPENDS = "alsa-lib zlib libogg yasm-native"

EXTRA_OECONF_append = " --disable-mipsdspr1 --disable-mipsdspr2 "
