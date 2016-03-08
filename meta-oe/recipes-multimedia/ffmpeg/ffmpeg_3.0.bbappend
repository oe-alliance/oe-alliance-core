RSUGGESTS_${PN} = ""

PROVIDES =+ " libavcodec53 libavformat53 libav"
PACKAGES =+ " libavcodec53 libavformat53 libav"

EXTRA_OECONF_append = " --disable-mipsdsp --disable-mipsdspr2 "

PACKAGECONFIG[avdevice] = "--enable-avdevice,--disable-avdevice"
