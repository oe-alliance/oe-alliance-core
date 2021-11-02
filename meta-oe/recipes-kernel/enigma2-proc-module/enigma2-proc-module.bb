SUMMARY = "Enigma proc module for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRCREV = "${AUTOREV}"
PV = "${DISTRO_NAME}-${IMAGE_VERSION}-${IMAGE_BUILD}-${MACHINEBUILD}"
PR = "r${DATE}"

PR[vardepsxeclude] += " DATE DATETIME"

PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "https://github.com/oe-alliance"

do_configure[nostamp] = "1"
do_configure[vardepsexclude] += " DATE DATETIME"
do_install[vardepsexclude] += " DATE"

SRC_URI = "git://github.com/oe-alliance/enigma2-proc-module.git;protocol=https"

S = "${WORKDIR}/git/source/epm"

inherit module gitpkgv

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_BUILDDIR}"

do_configure_prepend(){
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BRAND_OEM@|${BRAND_OEM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BUILD_VERSION@|${BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DATETIME@|${DATETIME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEFAULTTUNE@|${DEFAULTTUNE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEVELOPER_BUILD_VERSION@|${DEVELOPER_BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_TYPE@|${DISPLAY_TYPE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_FEED_URI@|${DISTRO_FEED_URI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_NAME@|${DISTRO_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_TYPE@|${DISTRO_TYPE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_AV_JACK@|${HAVE_AV_JACK}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_CI@|${HAVE_CI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_DVI@|${HAVE_DVI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI@|${HAVE_HDMI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI_IN_FHD@|${HAVE_HDMI_IN_FHD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI_IN_HD@|${HAVE_HDMI_IN_HD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_MINITV@|${HAVE_MINITV}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_RCA@|${HAVE_RCA}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SCART@|${HAVE_SCART}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SCART_YUV@|${HAVE_SCART_YUV}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_WOL@|${HAVE_WOL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_WWOL@|${HAVE_WWOL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_YUV@|${HAVE_YUV}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGEDIR@|${IMAGEDIR}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_FSTYPES@|${IMAGE_FSTYPES}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_NAME@|${IMAGE_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_VERSION@|${IMAGE_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@KERNEL_FILE@|${KERNEL_FILE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINE@|${MACHINE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINEBUILD@|${MACHINEBUILD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINE_BRAND@|${MACHINE_BRAND}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINE_NAME@|${MACHINE_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MKUBIFS_ARGS@|${MKUBIFS_ARGS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MTD_KERNEL@|${MTD_KERNEL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MTD_ROOTFS@|${MTD_ROOTFS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@ROOTFS_FILE@|${ROOTFS_FILE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@SOC_FAMILY@|${SOC_FAMILY}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@TRANSCODING@|${TRANSCODING}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@UBINIZE_ARGS@|${UBINIZE_ARGS}|g"
}

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/epm
	install -m 0644 ${S}/epm.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/epm/
	install -d ${D}${sysconfdir}/modules-load.d
	install -m 0644 ${WORKDIR}/git/modules-load.d/epm.conf ${D}${sysconfdir}/modules-load.d/
}
