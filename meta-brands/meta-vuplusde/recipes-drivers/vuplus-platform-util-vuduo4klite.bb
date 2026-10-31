require vuplus-platform-util.inc

SRCDATE = "20261026"

KV = "4.1.20"

PR = "r4"

# V3D owns the Nexus libraries copied to /usr/lib by the platform init script.
# Keep them while the boot-critical platform is installed, even when the last
# optional EGL application (RetroGaming, Kodi or Chromium) is auto-removed.
RDEPENDS:${PN} += "vuplus-v3ddriver-vuduo4klite"

VENDOR_DRIVER_FIX_PROFILE = "vuduo4klite"
require recipes-bsp/vendor-driver-fixes/vendor-driver-fixes.inc

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

SRC_URI[md5sum] = "b7e5d2421ef06ddcf15203e41f529d30"
SRC_URI[sha256sum] = "24f34ab13d4c21a87e6191861d13a39264d4fd079e1ab1369d6bb004ec3aaf77"
