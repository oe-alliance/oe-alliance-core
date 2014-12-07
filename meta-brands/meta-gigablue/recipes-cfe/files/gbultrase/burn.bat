flash -noheader -forceerase usbdisk0:gigablue/ultrase/kernel.bin flash0.kernel

flash -noheader -forceerase usbdisk0:gigablue/ultrase/rootfs.bin flash0.rootfs

setenv -p STARTUP "boot -z -elf flash0.kernel:"

boot -z -elf flash0.kernel:
