require gigablue-platform-util-pro.inc

SRCDATE = "20261020.r0"

KV = "4.1.20"

PR = "r0"

# V3D owns the Nexus libraries copied to /usr/lib by the platform init script.
# Keep them while the boot-critical platform is installed, even when the last
# optional EGL application (RetroGaming, Kodi or Chromium) is auto-removed.
RDEPENDS:${PN} += "gb-v3ddriver-gb7252pro"

VENDOR_DRIVER_FIX_PROFILE = "platform"
require recipes-bsp/vendor-driver-fixes/vendor-driver-fixes.inc

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

SRC_URI[md5sum] = "f70c47b9b2d31158792d36d85ecb4855"
SRC_URI[sha256sum] = "f7d8bfafe7940c33354b3c1cce879038f5730e0ea3423ef8cc6f6bfc6e292c12"
