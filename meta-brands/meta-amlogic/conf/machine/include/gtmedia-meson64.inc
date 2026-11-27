# Common architecture and image naming for GTMedia 64-bit Meson receivers.
MACHINE_BRAND = "GTMedia"
BRAND_OEM = "gtmedia"
DEFAULTTUNE = "aarch64"
require conf/machine/include/soc-family.inc
require conf/machine/include/arm/arch-armv8a.inc
require conf/machine/box-hardware.inc

KERNEL_IMAGETYPE = "Image"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
IMAGEDIR = "${MACHINE}"
AMLOGIC_SOURCE_MIRROR ?= "https://source.mynonpublic.com/amlogic"
