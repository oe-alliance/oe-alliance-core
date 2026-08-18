# The stage0 snapshot only ever served to bootstrap rustc and cargo, and both
# now come prebuilt. Dropping it saves download and unpack; the rust sources
# themselves stay, libstd-rs still builds the target std from them.

SRC_URI:remove = "\
    ${RUST_DIST_SERVER}/dist/${RUST_STD_SNAPSHOT}.tar.xz;name=rust-std-snapshot-${RUST_BUILD_ARCH};subdir=rust-snapshot-components \
    ${RUST_DIST_SERVER}/dist/${RUSTC_SNAPSHOT}.tar.xz;name=rustc-snapshot-${RUST_BUILD_ARCH};subdir=rust-snapshot-components \
    ${RUST_DIST_SERVER}/dist/${CARGO_SNAPSHOT}.tar.xz;name=cargo-snapshot-${RUST_BUILD_ARCH};subdir=rust-snapshot-components \
"
