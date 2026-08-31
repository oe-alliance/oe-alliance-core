require gigablue-platform-util-pro.inc

SRCDATE = "20260526.r0"

KV = "4.1.20"

PR = "r1"

# The current gb7252pro platform archive no longer ships the small `config`
# RPC client although its init script still creates /usr/bin/config and Kodi
# needs it to control only dvb_init's retained Enigma2 graphics plane.  Keep
# the missing vendor client next to this machine recipe instead of fetching a
# complete platform package for a different receiver.
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}/files:"
SRC_URI += "file://config"

do_install:append() {
    install -m 0755 ${UNPACKDIR}/config ${D}/home/root/platform/config
}

SRC_URI[md5sum] = "a9b3f9b11029805276724a1315c31cf0"
SRC_URI[sha256sum] = "4fd486a8d1ab5dbd4456caddea237454f1bb636ae3ea3417e8de8b99c2650042"
