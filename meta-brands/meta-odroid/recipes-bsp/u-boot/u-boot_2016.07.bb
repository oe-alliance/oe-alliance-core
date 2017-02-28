require u-boot.inc

DEPENDS += "dtc-native fip-native"

# This revision corresponds to the tag "v2016.07"
# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "4579720412744dd13266a3505bb38ce2da819b4f"

SRC_URI += "file://0001-add-odroid-funnkyness.patch \
	file://aml_encrypt_gxb \
	file://bl2.package  \
	file://bl301.bin \
	file://bl30.bin \
	file://bl31.bin \
	file://bl1.bin.hardkernel \
	file://0001-mmc-Add-Amlogic-Meson-driver.patch \
	file://0001-arm-amlogic-Enable-MMC-driver-on-Odroid-C2.patch \
	"

PV = "v2016.07+git${SRCPV}"

do_compile_prepend () {
	mkdir -p fip/gxb
	mkdir -p sd_fuse
	cp ${WORKDIR}/bl301.bin ${S}/fip/gxb
	cp ${WORKDIR}/bl30.bin ${S}/fip/gxb
	cp ${WORKDIR}/bl31.bin ${S}/fip/gxb
	cp ${WORKDIR}/bl2.package ${S}/fip/gxb
	cp ${WORKDIR}/aml_encrypt_gxb ${S}/fip/gxb
	cp ${WORKDIR}/bl1.bin.hardkernel ${S}/sd_fuse
}

do_compile_append () {
       cp ${S}/sd_fuse/u-boot.bin ${S}/u-boot.bin
}

do_install_append () {
       install -d ${D}/boot
       install ${S}/sd_fuse/bl1.bin.hardkernel ${D}/boot
       install ${S}/sd_fuse/u-boot.bin ${D}/boot
}

do_deploy_append () {
       install ${WORKDIR}/bl1.bin.hardkernel ${DEPLOY_DIR_IMAGE}
       install ${S}/sd_fuse/u-boot.bin ${DEPLOY_DIR_IMAGE}
}

UBOOT_MACHINE_odroidc2 = "odroid-c2_defconfig"
COMPATIBLE_MACHINE = "odroidc2"
