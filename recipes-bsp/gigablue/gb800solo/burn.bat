flash -noheader usbdisk0:vmlinux.gz flash0.kernel 
flash -noheader usbdisk0:rootfs.img flash0.rootfs 
setenv -p STARTUP "boot -z -elf flash0.kernel: 'rootfstype=jffs2 bmem=106M@150M root=/dev/mtdblock6 rw '"  

