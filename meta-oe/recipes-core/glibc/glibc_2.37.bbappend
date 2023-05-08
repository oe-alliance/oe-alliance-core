FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-2.37:"

SRC_URI:remove = "file://0001-CVE-2021-38604.patch file://0002-CVE-2021-38604.patch"

SRC_URI += " file://0001-ptrace-protect-ptrace_peeksiginfo_args-from-redefint.patch \
             file://0002-fix-build-for-old-libcheader.patch \
             file://0004-sunrpc-use-snprintf-instead-of-an-implied-length-gua.patch \
             file://0005-Revert-Linux-Use-32-bit-vDSO-for-clock_gettime-getti.patch \
"

SRC_URI:append:arm = " file://tls-libwidevinecdm.so-since-4.10.2252.0-has-TLS-with.patch \
                       file://glibc-HACK-Don-t-check-GLIBC_ABI_DT_RELR-support-for-Chrom.patch \
"

SRC_URI:append = " file://0006-remove-arc4random-implementation.patch"

SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"

CFLAGS:append = " -Wno-maybe-uninitialized"

OLDEST_KERNEL = "3.2.0"
