FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-build-with-older-cairo-version.patch"

do_configure:prepend() {
    # downgrade cairo depends to version 1.17
    sed -i \
        -e '/^\[package.metadata.system-deps.cairo.v1_18\]/{n;s/.*/version = "1.17"/}' \
        -e '/^\[package.metadata.system-deps.cairo-gobject.v1_18\]/{n;s/.*/version = "1.17"/}' \
        ${UNPACKDIR}/cargo_home/bitbake/cairo-sys-rs-*/Cargo.toml
}
