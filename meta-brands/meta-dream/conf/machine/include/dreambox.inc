# dreambox.inc
# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dreambox"
MACHINE_BRAND = "Dreambox"

EXTRA_IMAGEDEPENDS += "dreambox-secondstage-${MACHINE}"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

include conf/machine/include/autoload-filesystems.inc

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dreambox-dvbmediasink \
    gst-plugin-dtsdownmix \
    ntfs-3g \
    kernel-module-cifs \
    kernel-module-autofs4 \	
    kernel-module-hmac \
    kernel-module-md4 \
    kernel-module-ecb \
    dreambox-boot-progress \
    tpmd \
    "
    
MACHINE_EXTRA_RRECOMMENDS_append_dm800se = " \
    kernel-module-fuse \
    kernel-module-nfsd \
    kernel-module-exportfs \
    "
	
MACHINE_EXTRA_RRECOMMENDS_append_dm500hd = " \
    kernel-module-fuse \
    kernel-module-nfsd \
    kernel-module-exportfs \
    "	

MACHINE_FEATURES += "dreambox alsa usbhost 3dtv switchoff tpm"
# Add "dreambox" override for recipes
MACHINEOVERRIDES .= ":dreambox"

PREFERRED_PROVIDER_virtual/bootloader = "dreambox-secondstage-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-dreambox"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"

PREFERRED_VERSION_linux-dreambox = "3.2"
PREFERRED_VERSION_linux-libc-headers = "3.2"
PREFERRED_VERSION_nfs-utils ?= "1.2.8"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "${@base_contains('MACHINE_FEATURES', 'usbconsole', 'ttyS0,115200', 'null', d)}"

DREAMBOX_BUILDIMAGE = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
    --flash-size ${DREAMBOX_FLASH_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZE}"
	
DREAMBOX_BUILDIMAGEV2 = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZEV2} \
    --flash-size ${DREAMBOX_FLASH_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZEV2}"
