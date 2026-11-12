FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# OpenBh / OpenViX: add a "decoder" property to dvbvideosink so a second instance can
# drive the PiP decoder (/dev/dvb/adapter0/video1) - used by servicemp3 PiP.
# Default is decoder 0, so playback without the property set is unchanged.
# OpenBh only: add AVC3 and Multiple SPS support.
SRC_URI:append:openbh = " file://0001-dvbvideosink-add-decoder-property.patch file://0002-dvbvideosink-h264-avc3-multiple-sps.patch file://0003-dvbaudiosink-vuplus-pad-dts-core-frames.patch"
SRC_URI:append:openvix = " file://0001-dvbvideosink-add-decoder-property.patch"

PR:append:openbh = ".3"
PR:append:openvix = ".1"
