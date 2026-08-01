FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

GCCPIE:pn-boost:aarch64 = "1"
SECURITY_PIE_CFLAGS:pn-boost:aarch64 = "-pie -fPIE"

# Boost.Context: upstream 1.91.0 default_abi deducts from build host (x86_64)
# instead of target; for mips32 we need abi=o32. OE-core has the analogue
# for arm/aarch64 in boost.inc, but not for mips. Upstream fix:
# https://github.com/boostorg/context/pull/334 (post-1.91.0)
BJAM_OPTS:append:mipsarch = " abi=o32 architecture=mips"

SRC_URI += "file://0001-process-v2-exit_code-guard-static_asserts-on-mips.patch"
