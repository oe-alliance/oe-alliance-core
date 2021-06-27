SUMMARY = "Enigma kernel information module for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
SRCREV = "${AUTOREV}"
PACKAGE_ARCH = "${MACHINEBUILD}"
SSTATE_SKIP_CREATION = "1"

SRC_URI = "git://github.com/oe-alliance/enigma2-kernel-module.git;protocol=git"

S = "${WORKDIR}/git/source/enigma"

inherit python3-dir module gitpkgv deploy

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_BUILDDIR}"

do_configure_prepend(){
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINE@|${MACHINE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_MODEL@|${MACHINE_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BOX_BRAND@|${BRAND_OEM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_BRAND@|${MACHINE_BRAND}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@STB_PLATFORM@|${STB_PLATFORM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_VERSION@|${IMAGE_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BUILD_VERSION@|${BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@LANGUAGE@|${LANGUAGE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEVELOPER_NAME@|${DEVELOPER_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_FEED_URI@|${DISTRO_FEED_URI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_NAME@|${DISTRO_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@OE_VERSION@|${OE_VER}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@KERNELVERSION@|${KERNEL_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@PREFERRED_VERSION_python@|${PYTHON_BASEVERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@PREFERRED_PROVIDER_virtual/enigma2-mediaservice@|${MEDIASERVICE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_MULTILIB@|${HAVE_MULTILIB}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEFAULTTUNE@|${DEFAULTTUNE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@SOC_FAMILY@|${SOC_FAMILY}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BLINDSCAN_BINARY@|${BLINDSCAN_BINARY}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@RCTYPE@|${RCTYPE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@RCNAME@|${RCNAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@RCIDNUM@|${RCIDNUM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SMALLFLASH@|${HAVE_SMALLFLASH}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_MIDDLEFLASH@|${HAVE_MIDDLEFLASH}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGEDIR@|${IMAGEDIR}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_FSTYPES@|${IMAGE_FSTYPES}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MTD_BOOTFS@|${MTD_BOOTFS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MTD_ROOTFS@|${MTD_ROOTFS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MTD_KERNEL@|${MTD_KERNEL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@ROOTFS_FILE@|${ROOTFS_FILE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@KERNEL_FILE@|${KERNEL_FILE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MKUBIFS_ARGS@|${MKUBIFS_ARGS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@UBINIZE_ARGS@|${UBINIZE_ARGS}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@FORCE@|${FORCE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DATE@|${DATE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@TARGET_FPU@|${TARGET_FPU}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_TYPE@|${DISPLAY_TYPE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_TRANSCODING@|${HAVE_TRANSCODING}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_MULTITRANSCODING@|${HAVE_MULTITRANSCODING}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI@|${HAVE_HDMI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_YUV@|${HAVE_YUV}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_RCA@|${HAVE_RCA}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_AV_JACK@|${HAVE_AV_JACK}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SCART@|${HAVE_SCART}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_DVI@|${HAVE_DVI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SVIDEO@|${HAVE_SVIDEO}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI_IN_HD@|${HAVE_HDMI_IN_HD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_HDMI_IN_FHD@|${HAVE_HDMI_IN_FHD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_WOL@|${HAVE_WOL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_WWOL@|${HAVE_WWOL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_CI@|${HAVE_CI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_VFDSYMBOL@|${HAVE_VFDSYMBOL}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_FHDSKIN@|${HAVE_FHDSKIN}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@SUPPORT_DBOXLCD@|${SUPPORT_DBOXLCD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_VERSION@|${DISTRO_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BUILD_VERSION@|${BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEVELOPER_BUILD_VERSION@|${DEVELOPER_BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_TYPE@|${DISTRO_TYPE}|g"
}

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_configure[nostamp] = "1"
do_install[vardepsexclude] += "DATE"

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/enigma
	modinfo -d ${S}/enigma.ko > ${S}/enigma.txt
	sed -i '1d' ${S}/enigma.txt
	sort  ${S}/enigma.txt > enigma-${MACHINE}.txt
	install -m 0644 ${S}/enigma.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/enigma/
	install -d ${D}${sysconfdir}/modules-load.d
	echo "enigma" > ${D}${sysconfdir}/modules-load.d/zzzzenigma.conf
}

FILES_${PN} += "${sysconfdir}"

export KCFLAGS = "-Wno-error"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/enigma-${MACHINE}.txt ${DEPLOY_DIR_IMAGE}/
}

addtask deploy before do_build after do_install
