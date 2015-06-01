KV = "3.3.1"
SRC = "2015"
SRCREV = "r3"
SRCDATE = "14052013"
SRCDATE_azboxhd = "02022014"

PR = "r2"
MACHINE_KERNEL_PR_append = ".4"

SRC_URI += "http://source.mynonpublic.com/linux-azbox-${KV}-new-2.tar.bz2;name=azbox-kernel \
       http://source.mynonpublic.com/${MACHINE}/${MACHINE}-${KV}-${SRC}-${SRCREV}.tar.gz;name=azbox-kernel-${MACHINE} \
       file://mips-refactor-clearpage-and-copypage.patch \
       file://defconfig \
       file://genzbf.c \
       file://sigblock.h \
       file://zboot.h \
       file://emhwlib_registers_tango2.h \
       file://sata.patch \
       "

SRC_URI_append_azboxhd += "http://source.mynonpublic.com/azbox/initramfs-${MACHINE}-oe-core-${KV}-${SRCDATE}.tar.bz2;name=azbox-initrd-${MACHINE} \
       file://hdide.patch \
       "

SRC_URI[azbox-kernel.md5sum] = "dfd04abeaf3741b3d2a44428ca5aeaa1"
SRC_URI[azbox-kernel.sha256sum] = "31b73397220d85aedf3c914026371fc1eeac67e3de09a5610b70b209d2a8b9df"
SRC_URI[azbox-kernel-azboxhd.md5sum] = "eac667385c3a2cf6009510f427113f62"
SRC_URI[azbox-kernel-azboxhd.sha256sum] = "c0d7340d2abdea62714d3c5a5c2296411a6d146f53160ee173c6b181feaad722"
SRC_URI[azbox-initrd-azboxhd.md5sum] = "be250b8a23c782ba569ebaa65956d7e1"
SRC_URI[azbox-initrd-azboxhd.sha256sum] = "2cd4c203ac1f321c8b4f4f011411a5f987b3ea64e61f16ce6df73e9e15d39d4f"

include recipes-linux/linux-azbox.inc

