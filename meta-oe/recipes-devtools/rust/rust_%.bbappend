# Install upstream's prebuilt rustc instead of bootstrapping it.
#
# rust-native exists only to cross-compile a few Rust based Python extensions.
# Bootstrapping it costs ~30 min, plus ~30 min for llvm-native which is pulled
# in solely as rustc's code generator. Upstream ships a prebuilt toolchain for
# this exact host triple, and the recipe already patchelf's such binaries for
# its stage0 snapshot, so the mechanism is established.
#
# oe-core disables do_fetch and deletes do_unpack for this recipe (shared rust
# source tree), so the tarballs are fetched by rust-prebuilt-native and staged
# under ${datadir}/rust-prebuilt. Everything else - target specs, wrappers,
# packaging, sysroot staging - stays exactly as oe-core defines it.
#
# Target standard libraries keep being built from source by libstd-rs, so
# tier-3 targets such as mipsel are unaffected.
#
# Rust 1.98 has most OE target triples built in (rust-lang/rust#157650), but not
# mipsel, so the rustc wrapper below stays. It is inert for built-in targets.

DEPENDS:remove:class-native = "llvm"
DEPENDS:append:class-native = " rust-prebuilt-native"

RUST_PREBUILT_STAGE = "${STAGING_DATADIR_NATIVE}/rust-prebuilt"
RUST_PREBUILT_SYS = "x86_64-unknown-linux-gnu"

# do_configure is a python task upstream, so disable rather than override it.
python () {
    if bb.data.inherits_class('native', d):
        d.setVarFlag('do_configure', 'noexec', '1')
        d.setVarFlag('do_rust_setup_snapshot', 'noexec', '1')
}

do_install:class-native () {
    # cargo is provided by oe-core's separate cargo-native recipe; installing
    # it here as well would collide in the native sysroot.
    for comp in rustc rust-std; do
        installer="${RUST_PREBUILT_STAGE}/${comp}-${PV}-${RUST_PREBUILT_SYS}/install.sh"
        if [ ! -f "$installer" ]; then
            bbfatal "missing prebuilt component: $comp"
        fi
        "$installer" --destdir=${D} --prefix=${prefix} --disable-ldconfig
    done

    if [ ! -e ${D}${bindir}/rustc ]; then
        bbfatal "prebuilt install produced no rustc in ${D}${bindir}"
    fi

    # Same uninative fixup the stage0 snapshot needs.
    if [ -n "${UNINATIVE_LOADER}" ] && [ -e "${UNINATIVE_LOADER}" ]; then
        for bin in ${D}${bindir}/rustc ${D}${libdir}/rustlib/*/bin/rust-lld ${D}${libdir}/rustlib/*/bin/gcc-ld/*; do
            if [ -f "$bin" ]; then
                patchelf "$bin" --set-interpreter ${UNINATIVE_LOADER}
            fi
        done
    fi

    rm -f ${D}${libdir}/rustlib/uninstall.sh ${D}${libdir}/rustlib/install.log
    rm -f ${D}${libdir}/rustlib/components ${D}${libdir}/rustlib/manifest-*

    # A stable rustc needs -Zunstable-options to load OE's target specs, and
    # RUSTFLAGS reaches it only through cargo, not direct callers.
    mv ${D}${bindir}/rustc ${D}${bindir}/rustc.real
    cat > ${D}${bindir}/rustc <<'EOF'
#!/bin/sh
RUSTC_BOOTSTRAP=1
export RUSTC_BOOTSTRAP
exec "$(dirname "$0")/rustc.real" -Zunstable-options "$@"
EOF
    chmod 0755 ${D}${bindir}/rustc
}

# librustc_driver and libLLVM come from upstream already built for release.
# Stripping them again uses the host binutils, and an older strip has been seen
# to produce a rustc that segfaults. Target builds use the cross strip and are
# not affected.
INHIBIT_SYSROOT_STRIP:class-native = "1"

do_install[depends] += "patchelf-native:do_populate_sysroot"
do_install[vardepsexclude] += "UNINATIVE_LOADER"
