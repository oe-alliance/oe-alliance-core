flash -noheader -forceerase usbdisk0:gigablue/ue/kernel.bin nandflash0.kernel

flash -noheader -forceerase usbdisk0:gigablue/ue/rootfs.bin nandflash0.rootfs

setenv -p STARTUP "boot -z -elf nandflash0.kernel:"

boot -z -elf nandflash0.kernel:
