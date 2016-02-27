SRCDATE = "20160225"

KV = "4.0.1"

SRC_URI[md5sum] = "157a0dd6164cdea1f86efa7e87b432c5"
SRC_URI[sha256sum] = "09892d64bb4d8b7369cedafe298d945ea825fa279cc137903af9b324ab794ffa"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
