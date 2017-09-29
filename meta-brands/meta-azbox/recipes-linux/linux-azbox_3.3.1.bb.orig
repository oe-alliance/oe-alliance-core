KV = "3.3.1"
PR = "r1"
SRC = "2015"
SRCREV = "r4"
SRCDATE = "14052013"
SRCDATE_azboxhd = "02022014"

SRC_URI += "http://source.mynonpublic.com/linux-azbox-${KV}-new-2.tar.bz2;name=azbox-kernel \
    http://source.mynonpublic.com/${MACHINE}/${MACHINE}-${KV}-${SRC}-${SRCREV}.tar.gz;name=azbox-kernel-${MACHINE} \
    file://mips-refactor-clearpage-and-copypage.patch \
    file://defconfig \
    file://genzbf.c \
    file://sigblock.h \
    file://zboot.h \
    file://emhwlib_registers_tango2.h \
    file://sata.patch \
    file://0001-kernel-add-support-for-gcc-5.patch \
    file://fixme-hardfloat.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://dvb_frontend-Multistream-support-3.3.patch \
    file://timeconst_perl5.patch \
    "

SRC_URI_append_azboxhd += "http://source.mynonpublic.com/azbox/initramfs-${MACHINE}-oe-core-${KV}-${SRCDATE}.tar.bz2;name=azbox-initrd-${MACHINE} \
       file://hdide.patch \
       "

SRC_URI[azbox-kernel.md5sum] = "dfd04abeaf3741b3d2a44428ca5aeaa1"
SRC_URI[azbox-kernel.sha256sum] = "31b73397220d85aedf3c914026371fc1eeac67e3de09a5610b70b209d2a8b9df"

SRC_URI[azbox-kernel-azboxhd.md5sum] = "df2576b23a9fb81f218fb6c24340805f"
SRC_URI[azbox-kernel-azboxhd.sha256sum] = "92d20d33aa457061ec859ad14ac4a856728f680b470d2c0143cd8837226c8fe6"
SRC_URI[azbox-initrd-azboxhd.md5sum] = "be250b8a23c782ba569ebaa65956d7e1"
SRC_URI[azbox-initrd-azboxhd.sha256sum] = "2cd4c203ac1f321c8b4f4f011411a5f987b3ea64e61f16ce6df73e9e15d39d4f"

include recipes-linux/linux-azbox.inc

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
