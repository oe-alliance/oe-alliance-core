SUMMARY = "Enigma kernel information module for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
SRCREV = "${AUTOREV}"

SSTATE_SKIP_CREATION = "1"

SRC_URI = "git://github.com/oe-alliance/enigma2-kernel-module.git;protocol=https;branch=master"

S = "${WORKDIR}/git/source/enigma"

inherit python3-dir module gitpkgv deploy

PACKAGE_ARCH = "${MACHINEBUILD}"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_BUILDDIR}"

do_configure:prepend(){
    if [ "${MACHINE}" = "vusolo4k" -o "${MACHINE}" = "vusolo2" -o "${MACHINE}" = "vusolose" -o "${MACHINE}" = "vuduo2" -o "${MACHINE}" = "vuuno4k" -o "${MACHINE}" = "vuuno4kse" -o "${MACHINE}" = "vuultimo4k" -o "${MACHINE}" = "vuzero4k" -o "${MACHINE}" = "vuduo4k" -o "${MACHINE}" = "vuduo4kse" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-VUPLUS-BASE}/recipes-drivers/vuplus-dvb-proxy-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "vuplus" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-VUPLUS-BASE}/recipes-drivers/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "amiko" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AMIKO-BASE}/recipes-drivers/amiko-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "beyonwiz" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BEYONWIZ-BASE}/recipes-drivers/beyonwiz-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "qviart" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-QVIART-BASE}/recipes-drivers/qviart-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "fulan" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FULAN-BASE}/recipes-drivers/fulan-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "LinkDroid" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-LINKDROID-BASE}/recipes-drivers/linkdroid-stb-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xtrend" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XTREND-BASE}/recipes-drivers/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "entwopia" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ENTWOPIA-BASE}/recipes-drivers/entwopia-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dinobot" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-DINOBOT-BASE}/recipes-drivers/dinobot-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dags" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-DAGS-BASE}/recipes-drivers/dags-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "gb7252" -o "${MACHINE}" = "gb72604" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GIGABLUE-BASE}/recipes-drivers/gigablue-platform-util-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "gigablue" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GIGABLUE-BASE}/recipes-drivers/gigablue-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "odin" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ODIN-BASE}/recipes-drivers/odin-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "octagon" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-OCTAGON-BASE}/recipes-drivers/octagon-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "uclan" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-UCLAN-BASE}/recipes-drivers/uclan-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-INI-BASE}/recipes-drivers/ini-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xp" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XP-BASE}/recipes-drivers/xp-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "cube" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-CUBE-BASE}/recipes-drivers/e2bmc-dvb-modules-cube.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ebox" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-EBOX-BASE}/recipes-drivers/ebox-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ixuss" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-IXUSS-BASE}/recipes-drivers/ixuss-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "azbox" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AZBOX-BASE}/recipes-drivers/azbox-dvb-modules.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "blackbox7405" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BLACKBOX-BASE}/recipes-drivers/blackbox-dvb-modules-blackbox7405.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "formuler" ]; then
        if [ "${MACHINE}" = "formuler1" ] || [ "${MACHINE}" = "formuler3" ] || [ "${MACHINE}" = "formuler4" ]; then
            DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FORMULER-BASE}/recipes-drivers/formuler-dvb-modules-${MACHINE}.bb | cut -b 12-19`
        else
            DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-FORMULER-BASE}/recipes-drivers/formuler-dvb-modules-al-${MACHINE}.bb | cut -b 12-19`
        fi
    elif [ "${BRAND_OEM}" = "skylake" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-SKYLAKE-BASE}/recipes-drivers/skylake-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "tiviar" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-TIVIAR-BASE}/recipes-drivers/tiviar-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "gfutures" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-GFUTURES-BASE}/recipes-drivers/gfutures-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "tripledot" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-TRIPLEDOT-BASE}/recipes-drivers/tripledot-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "airdigital" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AIRDIGITAL-BASE}/recipes-drivers/airdigital-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ceryon" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-CERYON-BASE}/recipes-drivers/ceryon-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "broadmedia" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-BROADMEDIA-BASE}/recipes-drivers/broadmedia-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "xcore" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-XCORE-BASE}/recipes-drivers/xcore-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ax" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-AX-BASE}/recipes-drivers/ax-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "ultramini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ULTRAMINI-BASE}/recipes-drivers/ultramini-dvb-modules-${MACHINE_DRIVER}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "wetek" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-WETEK-BASE}/recipes-drivers/wetek-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "protek" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-PROTEK-BASE}/recipes-drivers/protek-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "edision" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-EDISION-BASE}/recipes-drivers/edision-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "maxytec" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-MAXYTEC-BASE}/recipes-drivers/maxytec-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "abcom" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ABCOM-BASE}/recipes-drivers/abcom-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${BRAND_OEM}" = "dreambox" ]; then
        if [ "${MACHINE}" = "dm7080" ]; then
            DRIVERSDATE="20190502"
        elif [ "${MACHINE}" = "dm820" ]; then
            DRIVERSDATE="20181018"
        elif [ "${MACHINE}" = "dm520" ]; then
            DRIVERSDATE="20180222"
        elif [ "${MACHINE}" = "dm800" ]; then
            DRIVERSDATE="20131228"
        elif [ "${MACHINE}" = "dm8000" ]; then
            DRIVERSDATE="20140604"
        elif [ "${MACHINE}" = "dm7020hd" ] || [ "${MACHINE}" = "dm7020hdv2" ]; then
            DRIVERSDATE="20161019"
        elif [ "${MACHINE}" = "dm800se" ]; then
            DRIVERSDATE="20151201"
        elif [ "${MACHINE}" = "dm800sev2" ]; then
            DRIVERSDATE="20151201"
        elif [ "${MACHINE}" = "dm900" ]; then
            DRIVERSDATE="20200226"
        elif [ "${MACHINE}" = "dm920" ]; then
            DRIVERSDATE="20190830"
        else
            DRIVERSDATE="20150618"
        fi
    else
        DRIVERSDATE='N/A'
    fi
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINE@|${MACHINE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@MACHINEBUILD@|${MACHINEBUILD}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_MODEL@|${MACHINE_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BOX_BRAND@|${BRAND_OEM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_BRAND@|${MACHINE_BRAND}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@STB_PLATFORM@|${STB_PLATFORM}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@FRIENDLY_FAMILY@|${FRIENDLY_FAMILY}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@IMAGE_VERSION@|${IMAGE_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@BUILD_VERSION@|${BUILD_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@LANGUAGE@|${LANGUAGE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEVELOPER_NAME@|${DEVELOPER_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_FEED_URI@|${DISTRO_FEED_URI}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISTRO_NAME@|${DISTRO_NAME}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DISPLAY_DISTRO@|${DISPLAY_DISTRO}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@OE_VERSION@|${OE_VER}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@KERNELVERSION@|${KERNEL_VERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@PREFERRED_VERSION_python@|${PYTHON_BASEVERSION}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@PREFERRED_PROVIDER_virtual/enigma2-mediaservice@|${MEDIASERVICE}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_MULTILIB@|${HAVE_MULTILIB}|g"
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DEFAULTTUNE@|${TUNE_PKGARCH}|g"
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
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@HAVE_SCART_YUV@|${HAVE_SCART_YUV}|g"
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
	find ${S}/ -type f -name "*.c" | xargs -r -L1 sed -i "s|@DRIVERSDATE@|${DRIVERSDATE}|g"
}


do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_configure[nostamp] = "1"
do_install[vardepsexclude] += "DATE"

print_md5hash() {
	printf "checksum=%s\n" $(md5sum "$1" | awk '{print $1}')
}

do_install[nostamp] = "1"

do_install() {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/enigma
	modinfo -d ${S}/enigma.ko > ${S}/enigma.txt
	sed -i '1d' ${S}/enigma.txt
	sort  ${S}/enigma.txt > enigma-${MACHINE}.txt
	print_md5hash ${S}/enigma-${MACHINE}.txt >> ${S}/enigma-${MACHINE}.txt
	install -d ${D}${libdir}
	install -m 0644 ${S}/enigma-${MACHINE}.txt ${D}${libdir}/enigma.info
	install -m 0644 ${S}/enigma.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/enigma/
	install -d ${D}${sysconfdir}/modules-load.d
	echo "enigma" > ${D}${sysconfdir}/modules-load.d/zzzzenigma.conf
}

FILES:${PN} += "${sysconfdir} ${libdir}"

export KCFLAGS = "-Wno-error"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${S}/enigma-${MACHINE}.txt ${DEPLOY_DIR_IMAGE}/
}

addtask deploy before do_package after do_install
