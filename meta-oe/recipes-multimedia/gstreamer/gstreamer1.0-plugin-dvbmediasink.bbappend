FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# OpenBh / OpenViX only: add a "decoder" property to dvbvideosink so a second instance can
# drive the PiP decoder (/dev/dvb/adapter0/video1) - used by servicemp3 PiP.
# Default is decoder 0, so playback without the property set is unchanged.
SRC_URI:append:openbh = " file://0001-dvbvideosink-add-decoder-property.patch"
SRC_URI:append:openvix = " file://0001-dvbvideosink-add-decoder-property.patch"

PR:append:openbh = ".1"
PR:append:openvix = ".1"
