FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI += "file://0001-ptrace-protect-ptrace_peeksiginfo_args-from-redefint.patch \
            file://0002-fix-build-for-old-libcheader.patch \
            file://Add-getrandom-implementation-BZ-17252.patch \
            file://110-sh-fix-gcc6.patch \
            file://binutils.patch \
"

SRC_URI_append_dm800 += "file://0001_signalfd_and_evendfd.patch"
SRC_URI_append_sh4 += "file://trap-sh4.patch"

#remove obsolete files conflicting with libnsl2 (implemented in OE sumo release)
do_install_append () {
    rm -f ${D}${includedir}/rpcsvc/yppasswd.h
    rm -f ${D}${nonarch_libdir}/libnsl.so
    rm -f ${D}${nonarch_libdir}/libnsl.a
}

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
