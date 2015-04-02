flash -noheader -forceerase usbdisk0:gigablue/x1/kernel.bin flash0.kernel

flash -noheader -forceerase usbdisk0:gigablue/x1/rootfs.bin flash0.rootfs

boot -z -elf flash0.kernel:
