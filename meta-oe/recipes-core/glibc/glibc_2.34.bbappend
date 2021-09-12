FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-2.34:"

SRCREV_glibc = "9acab0bba6a5a57323b1f94bf95b21618a9e5aa4"

SRC_URI:remove = "file://0001-CVE-2021-38604.patch file://0002-CVE-2021-38604.patch"

SRC_URI += " file://0001-ptrace-protect-ptrace_peeksiginfo_args-from-redefint.patch \
             file://0002-fix-build-for-old-libcheader.patch \
             file://0003-glibc-c-utf8-locale.patch \
             file://0004-sunrpc-use-snprintf-instead-of-an-implied-length-gua.patch \
             file://0005-Revert-Linux-Use-32-bit-vDSO-for-clock_gettime-getti.patch \
"

SRC_URI:append:arm = " file://stdlib-canonicalize-realpath_stk-dest-maybe-uninit.patch \
                       file://add-support-for-SHT_RELR-sections.patch \
                       file://tls-libwidevinecdm.so-since-4.10.2252.0-has-TLS-with.patch \
"

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
