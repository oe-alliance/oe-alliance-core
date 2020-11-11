KV = "4.10.12"
SRCDATE = "20201103"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require ceryon-dvb-modules.inc
SRC_URI_remove = "http://source.mynonpublic.com/ceryon/ceryon-${MACHINE}-dvbdrive-${SRCDATE}.zip"

SRC_URI = "file://ceryon-${MACHINE}-dvbdrive-${SRCDATE}.zip"
