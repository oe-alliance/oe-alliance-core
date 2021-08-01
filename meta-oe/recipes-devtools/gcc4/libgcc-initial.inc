require libgcc-common.inc

DEPENDS = "linux-libc-headers virtual/${TARGET_PREFIX}gcc-initial"

LICENSE = "GPL-3.0-with-GCC-exception"

STAGINGCC = "gcc-cross-initial-${TARGET_ARCH}"
STAGINGCC:class-nativesdk = "gcc-crosssdk-initial-${SDK_SYS}"
PATH:prepend = "${STAGING_BINDIR_TOOLCHAIN}.${STAGINGCC}:"

PACKAGES = ""

EXTRA_OECONF += "--disable-shared"

COMPILERINITIAL = "-initial"

inherit nopackages
