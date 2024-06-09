SUMMARY = "Dreambox TPM Daemon"
DEPENDS = "openssl10"
PRECOMPILED_ARCH = "aarch64"
LICENSE = "CLOSED"

SRC_URI[aarch64.md5sum] = "07b99bfa6634e9e7e2e9f9922634c25d"
SRC_URI[aarch64.sha256sum] = "50c33c00ba47c508a24e872287bc344be6ceb44bb4103c08af65b127293757e6"

inherit opendreambox-precompiled-binary4 update-rc.d

SRC_URI:append = " \
    file://libsystemd.so.0.17.0 \
"

do_install:append() {
    install -d ${D}${libdir}
    install -m 0755 ${UNPACKDIR}/libsystemd.so.0.17.0 ${D}${libdir}/
    ln -s libsystemd.so.0.17.0 ${D}${libdir}/libsystemd.so.0
}

INITSCRIPT_NAME = "${PN}"
INHIBIT_PACKAGE_STRIP = "1"
INITSCRIPT_PARAMS = "start 60 S ."

FILES:${PN}-src += " \
    /lib/systemd/system/tpmd.socket \
    /lib/systemd/system/tpmd.service \
"

INSANE_SKIP:${PN} += "file-rdeps"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"
