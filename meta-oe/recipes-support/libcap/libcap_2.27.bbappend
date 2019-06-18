SUMMARY = "Library for getting/setting POSIX.1e capabilities"
HOMEPAGE = "http://sites.google.com/site/fullycapable/"

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILESEXTRAPATHS_append := "${THISDIR}/files"

SRC_URI_append_sh4 = " \
    file://libcap_old_kernel_fix.patch;patch=1 \
    "
