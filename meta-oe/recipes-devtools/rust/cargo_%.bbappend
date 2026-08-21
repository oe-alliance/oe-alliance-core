DEPENDS:append:class-native = " rust-prebuilt-native"

RUST_PREBUILT_STAGE = "${STAGING_DATADIR_NATIVE}/rust-prebuilt"
RUST_PREBUILT_SYS = "${BUILD_ARCH}-unknown-linux-gnu"

python () {
    if bb.data.inherits_class('native', d):
        d.setVarFlag('do_configure', 'noexec', '1')
        d.setVarFlag('do_compile', 'noexec', '1')
        d.setVarFlag('do_cargo_setup_snapshot', 'noexec', '1')
}

do_install:class-native () {
    src="${RUST_PREBUILT_STAGE}/cargo-${PV}-${RUST_PREBUILT_SYS}/cargo/bin/cargo"
    if [ ! -f "$src" ]; then
        bbfatal "prebuilt cargo not staged at $src"
    fi

    install -d ${D}${bindir}
    install -m 755 "$src" ${D}${bindir}/cargo

    if [ -n "${UNINATIVE_LOADER}" ] && [ -e "${UNINATIVE_LOADER}" ]; then
        patchelf ${D}${bindir}/cargo --set-interpreter ${UNINATIVE_LOADER}
    fi
}

do_install[depends] += "patchelf-native:do_populate_sysroot"
do_install[vardepsexclude] += "UNINATIVE_LOADER"

INHIBIT_SYSROOT_STRIP:class-native = "1"
INSANE_SKIP:${PN}:class-native = "already-stripped"
