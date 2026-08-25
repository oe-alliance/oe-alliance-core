FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

GCCPIE:pn-boost:aarch64 = "1"
SECURITY_PIE_CFLAGS:pn-boost:aarch64 = "-pie -fPIE"

SRC_URI += "file://0001-process-v2-exit_code-guard-static_asserts-on-mips.patch"
