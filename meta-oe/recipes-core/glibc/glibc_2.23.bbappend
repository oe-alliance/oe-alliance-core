FILESEXTRAPATHS:prepend := "${THISDIR}/${P}:"

SRC_URI += " file://0001-ptrace-protect-ptrace_peeksiginfo_args-from-redefint.patch \
             file://0002-fix-build-for-old-libcheader.patch \
             file://Add-getrandom-implementation-BZ-17252.patch \
             file://110-sh-fix-gcc6.patch \
             file://sh4-trap.patch \
"

SRC_URI:append:dm800 = " file://0001_signalfd_and_evendfd.patch"

#remove obsolete conflicting files
do_install:append() {
    rm -f ${D}${nonarch_base_libdir}/libcrypt-2.23.so
    rm -f ${D}${nonarch_base_libdir}/libcrypt.so.1
    rm -f ${D}${nonarch_base_libdir}/libcrypt.so
    rm -f ${D}${libdir}/libcrypt.a
    rm -f ${D}${libdir}/libcrypt.so
    rm -f ${D}${libdir}/libnsl.so
    rm -f ${D}${libdir}/libnsl.a
    rm -f ${D}${includedir}/crypt.h
    rm -rf ${D}${includedir}/rpcsvc
}

SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
