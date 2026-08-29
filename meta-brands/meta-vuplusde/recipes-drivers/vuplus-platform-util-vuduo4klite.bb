require vuplus-platform-util.inc

SRCDATE = "20260728"

KV = "4.1.20"

PR = "r1"

# The Duo 4K Lite dvb_init service exposes the same RPC interface as the
# GigaBlue BCM7252 platform.  Vu's archive creates /usr/bin/config in its init
# script but does not actually ship the client.  Keep only that compatible
# vendor RPC client next to this machine recipe; no GigaBlue platform package
# or driver is downloaded or installed.
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}/files:"
SRC_URI += "file://config"

do_install:append() {
    install -m 0755 ${UNPACKDIR}/config ${D}/home/root/platform/config
}

SRC_URI[md5sum] = "91004f70fc8b22fd54ad0bba12c8922a"
SRC_URI[sha256sum] = "11825fb9145ee77cdf60daffdc49fd4ad81a346a9f665b55a40d0b47e0f42729"
