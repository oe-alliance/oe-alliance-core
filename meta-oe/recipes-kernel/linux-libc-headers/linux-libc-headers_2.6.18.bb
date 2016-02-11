require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

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
        file://if-alg-header.patch \
        file://socket-protocol.patch \
"
SRC_URI[md5sum] = "6badae7fe759131d9ba334bf3864a4ab"
SRC_URI[sha256sum] = "ebbebe73aebdf235413763e78eddd48a16738630305ad298635ab25e83d6ebc6"

SSTATE_DUPWHITELIST += "${STAGING_INCDIR}/scsi/scsi_ioctl.h ${STAGING_INCDIR}/scsi/sg.h"
