FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://relax-on-setuptools-version.patch"

include python3-package-split.inc
