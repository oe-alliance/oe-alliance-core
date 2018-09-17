inherit module

SUMMARY = "Open Source Mali Utgard GPU Kernel Drivers"
SRC_URI = "https://developer.arm.com/-/media/Files/downloads/mali-drivers/kernel/mali-utgard-gpu/${MALI_PACKAGE_NAME}.tgz;name=driver"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://driver/src/devicedrv/mali/readme.txt;md5=92d15b487d204ace57072c48697b4a89"

EXTRA_OEMAKE ?= ""

S = "${WORKDIR}/${MALI_PACKAGE_NAME}/"

do_compile() {
	oe_runmake -C ${STAGING_KERNEL_DIR} \
		M=$(pwd)/driver/src/devicedrv/mali \
		O=${STAGING_KERNEL_BUILDDIR} \
		EXTRA_CFLAGS="${MALI_FLAGS}" \
		${MALI_KCONFIG}
}

do_install() {
	oe_runmake -C ${STAGING_KERNEL_DIR} \
		M=$(pwd)/driver/src/devicedrv/mali \
		INSTALL_MOD_PATH="${D}" \
		O=${STAGING_KERNEL_BUILDDIR} \
		EXTRA_CFLAGS="${MALI_FLAGS}" \
		${MALI_KCONFIG} \
		modules_install
}

do_install_append() {
	install -d ${D}${sysconfdir}/modules-load.d
	echo mali > ${D}${sysconfdir}/modules-load.d/_mali.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_mali.conf"

python() {
	platform = d.getVar('MALI_DRIVER_PLATFORM', True)
	if not platform:
		platform = "devicetree"
		TARGET_PLATFORM=" mali450"

	config = ["CONFIG_MALI_SHARED_INTERRUPTS=y",
		"CONFIG_MALI400=m",
		"CONFIG_MALI450=y",
		"CONFIG_MALI_DVFS=y",
		"CONFIG_GPU_AVS_ENABLE=y"]

	for c in config:
		d.appendVar('MALI_FLAGS', '-D' + c + ' ')
		d.appendVar('MALI_KCONFIG', c + ' ')
}