require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "r5"

SRC_URI += " \
        file://mips-add-missing-headers.patch \
        file://mips-fix-ptrace-header.patch \
        file://mips-brcm-add-missing-syscalls.patch \
        file://dvb-api-2.6.18-5.3.patch \
        file://fix-linux-futex-h.patch \
        file://mips-fix-64bit-types.patch \
        file://linux-2.6.18-include-asm.patch \
        file://linux-2.6.18-include-linux.patch \
        file://linux-2.6.18-include-linux-add-missing-headers.patch \
        file://linux-2.6.18-dm-ioctl_h.patch \
        file://linux-2.6.18-rfkill.patch \
        file://ppp-over-l2tp.patch \
        file://ifpacket_linuxtypes.patch \
        file://pktsched_linuxtypes.patch \
        file://makefile_make3-8-2.patch \
        file://mips-utimensat.patch \
        file://mips-pps.patch \
"
SRC_URI[md5sum] = "296a6d150d260144639c3664d127d174"
SRC_URI[sha256sum] = "c95280ff6c5d2a17788f7cc582d23ae8a9a7ba3f202ec6e4238eaadfce7c163d"

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
