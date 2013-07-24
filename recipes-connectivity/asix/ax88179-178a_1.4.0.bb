DESCRIPTION = "ASIX AX88179_178A USB 3.0/2.0 Gigabit Ethernet Network Adapter"
HOMEPAGE = "http://www.asix.com.tw/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://readme;endline=19;md5=f87a675da5e11ab9def922704bdda58b"

inherit module machine_kernel_pr

SRC_URI = "http://www.asix.com.tw/FrootAttach/driver/AX88179_178A_LINUX_DRIVER_v1.4.0_SOURCE.tar.bz2"

SRC_URI_append_dm500hd = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm8000 = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm7020hd = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm7020hdv2 = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm800se = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm500hdv2 = " \
			file://dreambox.patch \
			"
SRC_URI_append_dm800sev2 = " \
			file://dreambox.patch \
			"
			
S = "${WORKDIR}/AX88179_178A_LINUX_DRIVER_v1.4.0_SOURCE"

MACHINE_KERNEL_PR_append = ".0"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	oe_runmake 'MODPATH={D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net' \
		'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
		'LINUX_SRC=${STAGING_KERNEL_DIR}' \
		'KDIR=${STAGING_KERNEL_DIR}' \
		'KERNDIR=${STAGING_KERNEL_DIR}' \
		'KSRC=${STAGING_KERNEL_DIR}' \
		'KERNEL_VERSION=${KERNEL_VERSION}' \
		'KVER=${KERNEL_VERSION}' \
		'CC=${KERNEL_CC}' \
		'AR=${KERNEL_AR}' \
		'LD=${KERNEL_LD}'
}

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}/*.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

SRC_URI[md5sum] = "dfad74d32b82cde468b265fa34c9d85e"
SRC_URI[sha256sum] = "0b440c3184674f4cc5840743d545c8ff68f07dec9c41ee07c79be85d162126cd"
