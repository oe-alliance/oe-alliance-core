flash -noheader -forceerase usbdisk0:gigablue/x3/kernel.bin flash0.kernel

flash -noheader -forceerase usbdisk0:gigablue/x3/rootfs.bin flash0.rootfs

boot -z -elf flash0.kernel:
