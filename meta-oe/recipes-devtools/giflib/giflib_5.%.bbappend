FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-linking.patch"

SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_INCDIR}/gif_lib.h"
