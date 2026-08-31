SUMMARY = "Upstream prebuilt Node.js for the build host"
DESCRIPTION = "Installs the official node binary from nodejs.org into the native \
sysroot. Its only consumer is qtwebengine, which runs node as the script \
interpreter for its GN build steps. Nothing ships node to the target."
HOMEPAGE = "http://nodejs.org"
LICENSE = "Apache-2.0 AND Artistic-2.0 AND BSD-2-Clause AND BSD-3-Clause AND BlueOak-1.0.0 AND ISC AND MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=edc0683b77d2c503217642fa000b5b31"

PV = "24.20.0"

NODE_PREBUILT_ARCH = "${@{'x86_64': 'x64', 'aarch64': 'arm64'}.get(d.getVar('BUILD_ARCH'), '')}"
NODE_PREBUILT_DIR = "node-v${PV}-linux-${NODE_PREBUILT_ARCH}"

# sha256 of the upstream dist tarballs, per build host arch
NODE_PREBUILT_SHA256[x86_64]  = "2f2c0da162318f0de47665410c7c8c2ed3d36c8f3105de4bbc61176c70a7cbf2"
NODE_PREBUILT_SHA256[aarch64] = "5f4ddab610c1ab2016b3c227cebdbf6d9495161487e4739c7b90090595f465f7"

SRC_URI = "https://nodejs.org/dist/v${PV}/${NODE_PREBUILT_DIR}.tar.xz"
SRC_URI[sha256sum] = "${@d.getVarFlag('NODE_PREBUILT_SHA256', d.getVar('BUILD_ARCH')) or ''}"

UPSTREAM_CHECK_URI = "https://nodejs.org/dist/"
UPSTREAM_CHECK_REGEX = "v(?P<pver>24(\.\d+)+)/"

S = "${UNPACKDIR}/${NODE_PREBUILT_DIR}"

PROVIDES = "nodejs-native"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir} ${D}${libdir} ${D}${includedir}
    cp -a ${S}/bin/. ${D}${bindir}/
    cp -a ${S}/lib/. ${D}${libdir}/
    cp -a ${S}/include/. ${D}${includedir}/
}

INHIBIT_SYSROOT_STRIP = "1"

# Upstream binaries: stripped already, and not built by us.
INSANE_SKIP:${PN} += "already-stripped arch staticdev ldflags textrel file-rdeps"
EXCLUDE_FROM_SHLIBS = "1"
