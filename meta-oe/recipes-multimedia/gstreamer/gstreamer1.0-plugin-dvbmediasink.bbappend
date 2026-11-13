FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# OpenBh / OpenViX: add a "decoder" property to dvbvideosink so a second instance can
# drive the PiP decoder (/dev/dvb/adapter0/video1) - used by servicemp3 PiP.
# Default is decoder 0, so playback without the property set is unchanged.
# Add ACV3 & multiple sps, fix dts core frames and driver playback issues.
SRC_URI:append:openbh = " file://0001-dvbvideosink-add-decoder-property.patch file://0002-dvbvideosink-h264-avc3-multiple-sps.patch file://0003-dvbaudiosink-vuplus-pad-dts-core-frames.patch file://0004-dvbaudiosink-dts-driver-fixes.patch"
SRC_URI:append:openvix = " file://0001-dvbvideosink-add-decoder-property.patch file://0002-dvbvideosink-h264-avc3-multiple-sps.patch file://0003-dvbaudiosink-vuplus-pad-dts-core-frames.patch file://0004-dvbaudiosink-dts-driver-fixes.patch"

PR:append:openbh = ".5"
PR:append:openvix = ".5"
