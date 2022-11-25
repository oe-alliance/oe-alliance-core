SUMMARY = "enigma.info used by BoxInfo"
PRIORITY = "required"
MAINTAINER = "oe-alliance team"

require conf/license/license-gplv2.inc

RCONFLICTS:${PN} = "enigma-kernel-module"
RREPLACES:${PN} = "enigma-kernel-module"

SSTATE_SKIP_CREATION = "1"

PACKAGE_ARCH = "${MACHINEBUILD}"
PV = "${@bb.utils.contains_any("DISTRO_NAME", "openvix", "${IMAGE_VERSION}.${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", "${DATE}", d)}"

PACKAGES = "${PN}"

S = "${WORKDIR}"

inherit python3-dir 

INFOFILE = "${libdir}/enigma.info"

export KERNEL_VERSION = "${@oe.utils.read_file('${PKGDATA_DIR}/kernel-depmod/kernel-abiversion')}"

do_compile(){
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
    elif [ "${BRAND_OEM}" = "anadol" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${OEA-META-ANADOL-BASE}/recipes-drivers/anadol-dvb-modules-${MACHINE}.bb | cut -b 12-19`
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

    mkdir -p ${S}${libdir}
    echo "architecture=${DEFAULTTUNE}" > ${S}${INFOFILE}
    echo "avjack=${HAVE_AV_JACK}" >> ${S}${INFOFILE}
    echo "blindscanbinary=${BLINDSCAN_BINARY}" >> ${S}${INFOFILE}
    echo "brand=${BRAND_OEM}" >> ${S}${INFOFILE}
    echo "ci=${HAVE_CI}" >> ${S}${INFOFILE}
    echo "compiledate='${DATE}'" >> ${S}${INFOFILE}
    echo "dboxlcd=${SUPPORT_DBOXLCD}" >> ${S}${INFOFILE}
    echo "developername=${DEVELOPER_NAME}" >> ${S}${INFOFILE}
    echo "displaybrand=${MACHINE_BRAND}" >> ${S}${INFOFILE}
    echo "displaydistro=${DISPLAY_DISTRO}" >> ${S}${INFOFILE}
    echo "displaymodel=${MACHINE_NAME}" >> ${S}${INFOFILE}
    echo "displaytype=${DISPLAY_TYPE}" >> ${S}${INFOFILE}
    echo "distro=${DISTRO_NAME}" >> ${S}${INFOFILE}
    echo "driversdate='${DRIVERSDATE}'" >> ${S}${INFOFILE}
    echo "dvi=${HAVE_DVI}" >> ${S}${INFOFILE}
    echo "feedsurl=${DISTRO_FEED_URI}" >> ${S}${INFOFILE}
    echo "fhdskin=${HAVE_FHDSKIN}" >> ${S}${INFOFILE}
    echo "forcemode=${FORCE}" >> ${S}${INFOFILE}
    echo "fpu=${TARGET_FPU}" >> ${S}${INFOFILE}
    echo "friendlyfamily=${FRIENDLY_FAMILY}" >> ${S}${INFOFILE}
    echo "hdmi=${HAVE_HDMI}" >> ${S}${INFOFILE}
    echo "hdmifhdin=${HAVE_HDMI_IN_FHD}" >> ${S}${INFOFILE}
    echo "hdmihdin=${HAVE_HDMI_IN_HD}" >> ${S}${INFOFILE}
    echo "imagebuild='${BUILD_VERSION}'" >> ${S}${INFOFILE}
    echo "imagedevbuild='${DEVELOPER_BUILD_VERSION}'" >> ${S}${INFOFILE}
    echo "imagedir=${IMAGEDIR}" >> ${S}${INFOFILE}
    echo "imagefs=${IMAGE_FSTYPES}" >> ${S}${INFOFILE}
    echo "imagetype=${DISTRO_TYPE}" >> ${S}${INFOFILE}
    echo "imageversion='${DISTRO_VERSION}'" >> ${S}${INFOFILE}
    echo "imglanguage=${LANGUAGE}" >> ${S}${INFOFILE}
    echo "imgrevision='${BUILD_VERSION}'" >> ${S}${INFOFILE}
    echo "imgversion='${IMAGE_VERSION}'" >> ${S}${INFOFILE}
    echo "kernel='${KERNEL_VERSION}'" >> ${S}${INFOFILE}
    echo "kernelfile=${KERNEL_FILE}" >> ${S}${INFOFILE}
    echo "machinebuild=${MACHINEBUILD}" >> ${S}${INFOFILE}
    echo "mediaservice=${MEDIASERVICE}" >> ${S}${INFOFILE}
    echo "middleflash=${HAVE_MIDDLEFLASH}" >> ${S}${INFOFILE}
    echo "mkubifs=${MKUBIFS_ARGS}" >> ${S}${INFOFILE}
    echo "model=${MACHINE}" >> ${S}${INFOFILE}
    echo "mtdbootfs=${MTD_BOOTFS}" >> ${S}${INFOFILE}
    echo "mtdkernel=${MTD_KERNEL}" >> ${S}${INFOFILE}
    echo "mtdrootfs=${MTD_ROOTFS}" >> ${S}${INFOFILE}
    echo "multilib=${HAVE_MULTILIB}" >> ${S}${INFOFILE}
    echo "multitranscoding=${HAVE_MULTITRANSCODING}" >> ${S}${INFOFILE}
    echo "oe=${OE_VER}" >> ${S}${INFOFILE}
    echo "platform=${STB_PLATFORM}" >> ${S}${INFOFILE}
    echo "python='${PYTHON_BASEVERSION}'" >> ${S}${INFOFILE}
    echo "rca=${HAVE_RCA}" >> ${S}${INFOFILE}
    echo "rcidnum=${RCIDNUM}" >> ${S}${INFOFILE}
    echo "rcname=${RCNAME}" >> ${S}${INFOFILE}
    echo "rctype=${RCTYPE}" >> ${S}${INFOFILE}
    echo "rootfile=${ROOTFS_FILE}" >> ${S}${INFOFILE}
    echo "scart=${HAVE_SCART}" >> ${S}${INFOFILE}
    echo "scartyuv=${HAVE_SCART_YUV}" >> ${S}${INFOFILE}
    echo "smallflash=${HAVE_SMALLFLASH}" >> ${S}${INFOFILE}
    echo "socfamily='${SOC_FAMILY}'" >> ${S}${INFOFILE}
    echo "svideo=${HAVE_SVIDEO}" >> ${S}${INFOFILE}
    echo "transcoding=${HAVE_TRANSCODING}" >> ${S}${INFOFILE}
    echo "ubinize=${UBINIZE_ARGS}" >> ${S}${INFOFILE}
    echo "vfdsymbol=${HAVE_VFDSYMBOL}" >> ${S}${INFOFILE}
    echo "wol=${HAVE_WOL}" >> ${S}${INFOFILE}
    echo "wwol=${HAVE_WWOL}" >> ${S}${INFOFILE}
    echo "yuv=${HAVE_YUV}" >> ${S}${INFOFILE}
    printf "checksum=%s\n" $(md5sum "${S}${INFOFILE}" | awk '{print $1}') >> ${S}${INFOFILE}
}

do_install[nostamp] = "1"

do_install() {
    install -d ${D}${libdir}
    install -m 0644 ${S}${INFOFILE} ${D}${INFOFILE}
}

FILES:${PN}:append = " /usr"
