SUMMARY = "enigma.info used by BoxInfo"
PRIORITY = "required"
MAINTAINER = "oe-alliance team"

require conf/license/license-gplv2.inc

deltask fetch
deltask unpack
deltask patch
deltask prepare_recipe_sysroot
deltask configure
deltask compile
deltask source_date_epoch

RCONFLICTS:${PN} = "enigma-kernel-module"
RREPLACES:${PN} = "enigma-kernel-module"

SSTATE_SKIP_CREATION = "1"

PACKAGE_ARCH = "${MACHINEBUILD}"
PV = "${IMAGE_VERSION}"
PR = "${@bb.utils.contains_any("DISTRO_NAME", "openvix openbh", "${IMAGE_BUILD}", "r${DATE}-${MACHINEBUILD}", d)}"
PR[vardepsexclude] = "DATE"

PACKAGES = "${PN}"

# if DATE in PR changes (next day), workdir name changes too
# this makes sstate unhappy and breakes many tasks in many weird ways

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

inherit python3-dir 

INFOFILE = "${libdir}/enigma.info"

export KERNEL_VERSION = "${@oe.utils.read_file('${STAGING_KERNEL_BUILDDIR}/kernel-abiversion')}"

do_install[nostamp] = "1"

do_install() {
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
            DRIVERSDATE="20200321"
        elif [ "${MACHINE}" = "dreamone" ]; then
            DRIVERSDATE="20210518"
        elif [ "${MACHINE}" = "dreamtwo" ]; then
            DRIVERSDATE="20210518"
        else
            DRIVERSDATE="20150618"
        fi
    else
        DRIVERSDATE='N/A'
    fi

    install -d ${D}${libdir}
    printf "architecture='${DEFAULTTUNE}'\n" > ${D}${INFOFILE}
    printf "avjack=${HAVE_AV_JACK}\n" >> ${D}${INFOFILE}
    printf "blindscanbinary='${BLINDSCAN_BINARY}'\n" >> ${D}${INFOFILE}
    printf "brand='${BRAND_OEM}'\n" >> ${D}${INFOFILE}
    printf "ci=${HAVE_CI}\n" >> ${D}${INFOFILE}
    printf "compiledate='${DATE}'\n" >> ${D}${INFOFILE}
    printf "dboxlcd=${SUPPORT_DBOXLCD}\n" >> ${D}${INFOFILE}
    printf "developername='${DEVELOPER_NAME}'\n" >> ${D}${INFOFILE}
    printf "displaybrand='${MACHINE_BRAND}'\n" >> ${D}${INFOFILE}
    printf "displaydistro='${DISPLAY_DISTRO}'\n" >> ${D}${INFOFILE}
    printf "displaymodel='${MACHINE_NAME}'\n" >> ${D}${INFOFILE}
    printf "displaytype='${DISPLAY_TYPE}'\n" >> ${D}${INFOFILE}
    printf "distro='${DISTRO_NAME}'\n" >> ${D}${INFOFILE}
    printf "driversdate='${DRIVERSDATE}'\n" >> ${D}${INFOFILE}
    printf "dvi=${HAVE_DVI}\n" >> ${D}${INFOFILE}
    printf "feedsurl='${DISTRO_FEED_URI}'\n" >> ${D}${INFOFILE}
    printf "fhdskin=${HAVE_FHDSKIN}\n" >> ${D}${INFOFILE}
    printf "fpu='${TARGET_FPU}'\n" >> ${D}${INFOFILE}
    printf "friendlyfamily='${FRIENDLY_FAMILY}'\n" >> ${D}${INFOFILE}
    printf "hdmi=${HAVE_HDMI}\n" >> ${D}${INFOFILE}
    printf "hdmifhdin=${HAVE_HDMI_IN_FHD}\n" >> ${D}${INFOFILE}
    printf "hdmihdin=${HAVE_HDMI_IN_HD}\n" >> ${D}${INFOFILE}
    printf "hdmistandbymode=${HDMISTANDBY_MODE}\n" >> ${D}${INFOFILE}
    printf "imagebuild='${BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imagedevbuild='${DEVELOPER_BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imagedir='${IMAGEDIR}'\n" >> ${D}${INFOFILE}
    printf "imagefs='${IMAGE_FSTYPES}'\n" >> ${D}${INFOFILE}
    printf "imagetype='${DISTRO_TYPE}'\n" >> ${D}${INFOFILE}
    printf "imageversion='${DISTRO_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imglanguage='${LANGUAGE}'\n" >> ${D}${INFOFILE}
    printf "imgrevision='${BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imgversion='${IMAGE_VERSION}'\n" >> ${D}${INFOFILE}
    printf "kernel='${KERNEL_VERSION}'\n" >> ${D}${INFOFILE}
    printf "kexecmb=${HAVE_KEXECMB}\n" >> ${D}${INFOFILE}
    printf "kernelfile='${KERNEL_FILE}'\n" >> ${D}${INFOFILE}
    printf "machinebuild='${MACHINEBUILD}'\n" >> ${D}${INFOFILE}
    printf "mediaservice='${MEDIASERVICE}'\n" >> ${D}${INFOFILE}
    printf "middleflash=${HAVE_MIDDLEFLASH}\n" >> ${D}${INFOFILE}
    printf "mkubifs='${MKUBIFS_ARGS}'\n" >> ${D}${INFOFILE}
    printf "model='${MACHINE}'\n" >> ${D}${INFOFILE}
    printf "mtdbootfs='${MTD_BOOTFS}'\n" >> ${D}${INFOFILE}
    printf "mtdkernel='${MTD_KERNEL}'\n" >> ${D}${INFOFILE}
    printf "mtdrootfs='${MTD_ROOTFS}'\n" >> ${D}${INFOFILE}
    printf "multilib=${HAVE_MULTILIB}\n" >> ${D}${INFOFILE}
    printf "multitranscoding=${HAVE_MULTITRANSCODING}\n" >> ${D}${INFOFILE}
    printf "oe='${OE_VER}'\n" >> ${D}${INFOFILE}
    printf "platform='${STB_PLATFORM}'\n" >> ${D}${INFOFILE}
    printf "python='${PYTHON_BASEVERSION}'\n" >> ${D}${INFOFILE}
    printf "rca=${HAVE_RCA}\n" >> ${D}${INFOFILE}
    printf "rcidnum=${RCIDNUM}\n" >> ${D}${INFOFILE}
    printf "rcname='${RCNAME}'\n" >> ${D}${INFOFILE}
    printf "rctype=${RCTYPE}\n" >> ${D}${INFOFILE}
    printf "rootfile='${ROOTFS_FILE}'\n" >> ${D}${INFOFILE}
    printf "scart=${HAVE_SCART}\n" >> ${D}${INFOFILE}
    printf "noscartswitch=${HAVE_NO_SCART_SWITCH}\n" >> ${D}${INFOFILE}
    printf "scartyuv=${HAVE_SCART_YUV}\n" >> ${D}${INFOFILE}
    printf "smallflash=${HAVE_SMALLFLASH}\n" >> ${D}${INFOFILE}
    printf "socfamily='${SOC_FAMILY}'\n" >> ${D}${INFOFILE}
    printf "svideo=${HAVE_SVIDEO}\n" >> ${D}${INFOFILE}
    printf "timerwakeupmode=${TIMERWAKEUP_MODE}\n" >> ${D}${INFOFILE}
    printf "transcoding=${HAVE_TRANSCODING}\n" >> ${D}${INFOFILE}
    printf "ubinize='${UBINIZE_ARGS}'\n" >> ${D}${INFOFILE}
    printf "vfdsymbol=${HAVE_VFDSYMBOL}\n" >> ${D}${INFOFILE}
    printf "wol=${HAVE_WOL}\n" >> ${D}${INFOFILE}
    printf "wwol=${HAVE_WWOL}\n" >> ${D}${INFOFILE}
    printf "yuv=${HAVE_YUV}\n" >> ${D}${INFOFILE}
    printf "checksum=%s\n" $(md5sum "${D}${INFOFILE}" | awk '{print $1}') >> ${D}${INFOFILE}
}

do_install[vardepsexclude] += " DATE DATETIME IMAGE_BUILD MACHINEBUILD"

FILES:${PN}:append = " /usr"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${D}${INFOFILE} ${DEPLOY_DIR_IMAGE}/enigma-${MACHINEBUILD}.txt
}

addtask deploy before do_package after do_install
