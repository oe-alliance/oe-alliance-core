FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI_append_sh4 += "\
	file://glibc-fix-with-old-kernel.patch \
	file://0001-ldd-Force-correct-RTLDLIST-for-Solus.patch \
	file://110-sh-fix-gcc6.patch \
	file://0001-misc-Support-fallback-stateless-shells-path-in-absen.patch \
	file://0002-sysdeps-Add-support-for-usr-lib32-as-a-system-librar.patch \
	file://0003-elf-ldconfig-Use-a-stateless-ld.so.conf.patch \
	file://fix-x64-abi.patch \
"

SRC_URI_append_dm800 += " file://0001_signalfd_and_evendfd.patch"

#remove obsolete conflicting files
do_install_append() {
    rm -f ${D}${nonarch_base_libdir}/libcrypt-2.25.so
    rm -f ${D}${nonarch_base_libdir}/libcrypt.so.1
    rm -f ${D}${nonarch_base_libdir}/libcrypt.so
    rm -f ${D}${libdir}/libcrypt.a
    rm -f ${D}${libdir}/libcrypt.so
    rm -f ${D}${libdir}/libnsl.so
    rm -f ${D}${libdir}/libnsl.a
    rm -f ${D}${includedir}/crypt.h
    rm -rf ${D}${includedir}/rpcsvc
}

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/netatalk/at.h ${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
