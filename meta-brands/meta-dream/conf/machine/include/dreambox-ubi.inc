inherit image_types_nfi

IMAGE_FSTYPES ?= "ubinfi"
UBI_VOLNAME = "rootfs"
UBINIZE_VOLSIZE ?= "0"
UBINIZE_DATAVOL ?= "0"

MTD_KERNEL = "mtd3"
MTD_ROOTFS = "mtd3"
KERNEL_FILE = "N/A"
ROOTFS_FILE = "rootfs.bin"
