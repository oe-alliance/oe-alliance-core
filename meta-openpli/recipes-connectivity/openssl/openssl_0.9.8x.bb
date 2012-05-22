require recipes-connectivity/openssl/openssl.inc

PR = "${INC_PR}.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

SRC_URI += "file://debian/ca.patch \
            file://debian/config-hurd.patch;apply=no \
            file://debian/debian-targets.patch \
            file://debian/engines-path.patch \
            file://debian/kfreebsd-pipe.patch;apply=no \
            file://debian/make-targets.patch \
            file://debian/man-dir.patch \
            file://debian/man-section.patch \
            file://debian/no-rpath.patch \
            file://debian/no-symbolic.patch \
            file://debian/pic.patch \
            file://debian/pkg-config.patch \
            file://debian/rc4-amd64.patch \
            file://debian/rehash-crt.patch \
            file://debian/rehash_pod.patch \
            file://debian/shared-lib-ext.patch \
            file://debian/stddef.patch \
            file://debian/version-script.patch \
            file://debian/perl-path.diff"

SRC_URI += "file://configure-targets.patch \
            file://shared-libs.patch \
            file://parallel-make-fix.patch"

SRC_URI[md5sum] = "ee17e9bc805c8cc7d0afac3b0ef78eda"
SRC_URI[sha256sum] = "7ce0c7f2c451070b4497ea7ca6f23eba6cef1a56db2e86e433f65926a7bc7497"

EXTRA_OECONF += "no-idea no-mdc2 no-rc5"

BBCLASSEXTEND = "native nativesdk"

PARALLEL_MAKEINST = ""
