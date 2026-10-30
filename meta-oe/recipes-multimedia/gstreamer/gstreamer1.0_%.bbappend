FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_NO_LOCALE = "1"
SRC_URI:append = " file://0001-revert-use-new-gst-adapter-get-buffer.patch"

# OpenBh / OpenViX only, see 0001-input-selector-revert-two-phase-sinkpad-switch.patch
SRC_URI:append:openbh = " file://0001-input-selector-revert-two-phase-sinkpad-switch.patch"
SRC_URI:append:openvix = " file://0001-input-selector-revert-two-phase-sinkpad-switch.patch"

# OpenATV only, see 0001-input-selector-release-active-sinkpad-lock-across-p.patch
SRC_URI:append:openatv = " file://0001-input-selector-release-active-sinkpad-lock-across-p.patch"
SRC_URI:append:openatv = " file://0002-input-selector-reconfigure-the-other-pads-after-the-active-one.patch"
