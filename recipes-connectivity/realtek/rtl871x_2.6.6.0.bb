DESCRIPTION = "Driver for Realtek 871x series USB 802.11b/g/n WiFi stick"
HOMEPAGE = "http://www.realtek.com.tw"
SECTION = "kernel/modules"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://../COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"


MACHINE_KERNEL_PR_append = ".5"

SRC_URI = "http://archiv.openmips.com/rtl871x.tar.gz;name=rtl871x \
           file://mipsel-compatibility.patch \
           file://COPYING"

SRC_URI[rtl871x.md5sum] = "9da979c487f5c61a78111faa16450220"
SRC_URI[rtl871x.sha256sum] = "5829d290dd60e5fb3de0f3eaa85b48de8b208ce8149b2f7d9b32c8ebc218ec82"

inherit module

S = "${WORKDIR}/rtl871x"

EXTRA_OEMAKE = "KERNDIR=${STAGING_KERNEL_DIR}"

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
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
	install -m 0644 8712u${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/
	install -d ${D}/etc/modutils
	echo 8712u > ${D}/etc/modutils/wlan8712u
}
