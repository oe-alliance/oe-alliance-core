flash -noheader -forceerase usbdisk0:gigablue/ultraue/kernel.bin flash0.kernel

flash -noheader -forceerase usbdisk0:gigablue/ultraue/rootfs.bin flash0.rootfs

boot -z -elf flash0.kernel:
