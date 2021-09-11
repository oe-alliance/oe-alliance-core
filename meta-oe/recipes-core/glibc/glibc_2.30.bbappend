FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI += " file://0001-ptrace-protect-ptrace_peeksiginfo_args-from-redefint.patch \
             file://0002-fix-build-for-old-libcheader.patch \
             file://0003-glibc-c-utf8-locale.patch \
             file://Fix_system_error_return_value.patch \
             file://Fix_ldconfig_truncate_after_patchelf.patch \
             ${@bb.utils.contains("TARGET_ARCH", "arm", "file://glibc-add-support-for-SHT_RELR-sections.patch", "", d)} \
             ${@bb.utils.contains("TARGET_ARCH", "arm", "file://glibc-tls-libwidevinecdm.so-since-4.10.2252.0-has-TLS-with.patch", "", d)} \
"

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
