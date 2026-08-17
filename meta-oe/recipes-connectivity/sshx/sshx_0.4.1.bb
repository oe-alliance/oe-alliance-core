SUMMARY = "Secure web-based collaborative terminal"
DESCRIPTION = "sshx shares a terminal session over the web. The client opens a \
pseudo-terminal, streams it to an sshx.io server over gRPC and prints a link \
that gives collaborators a live, multiplayer view of it. Session contents are \
encrypted with a key that stays in the link fragment, so the server relays \
ciphertext only."
HOMEPAGE = "https://sshx.io"
BUGTRACKER = "https://github.com/ekzhang/sshx/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "crate://crates.io/sshx/${PV};name=sshx"
SRC_URI[sshx.sha256sum] = "d404b6fd09d82c7269fb8dcdec8b8f3c89aaf54c4e94b992d81d08e4121cfcc5"
S = "${CARGO_VENDORING_DIRECTORY}/sshx-${PV}"

inherit cargo cargo-update-recipe-crates upx-compress

# sshx-core generates its gRPC bindings in build.rs, and tonic-build wants a
# protoc binary rather than shipping one.
DEPENDS += "protobuf-native"
export PROTOC = "${STAGING_BINDIR_NATIVE}/protoc"
export PROTOC_INCLUDE = "${STAGING_INCDIR_NATIVE}"

DEPENDS += "libstd-rs"

require ${BPN}-crates.inc

# lzma keeps the compression time sane on a binary this size
UPX_ARGS += "--lzma"
