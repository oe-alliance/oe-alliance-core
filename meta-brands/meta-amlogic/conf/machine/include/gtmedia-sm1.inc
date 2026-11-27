# Shared SM1 architecture and video decoder capabilities.
SOC_FAMILY = "amlogic-sm1"
require conf/machine/include/gtmedia-meson64.inc

DVBMEDIASINK_CONFIG = "--with-pcm --with-eac3 --with-dts --with-aac-loas --with-audio-device=/dev/dvb/adapter0/dreamaudio0 --with-h265 --with-wmv --with-vb9 --with-vb6=no --with-vb8=no --with-spark=no --with-h263=no"
