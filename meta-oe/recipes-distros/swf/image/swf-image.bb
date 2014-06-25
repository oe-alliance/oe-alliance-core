SUMMARY = "SWF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "SWF"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2-pliplugins swf-feeds "

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
    oe-alliance-base \
    swf-enigma2 \
    swf-bootlogo \
    swf-spinner \
    swf-version-info \
    enigma2-plugin-extensions-bmediacenter-swf \
    enigma2-plugin-settings-default-swf \
    ${ENIGMA2_PLUGINS} \
    ${ENIGMA2_INI_PLUGINS} \
    ${@base_contains("MACHINE", "inihdx", "${ENIGMA2_USB_DRV}" , "", d)} \
    ntfs-3g \
    hddtemp \
    busybox-cron \
    python-gdata \
    ushare \
    ofgwrite \
    libshowiframe \
    task-base-smbfs-client \
    task-base-smbfs \
    task-base-nfs \
    mc \
    swf-base \
    "

ENIGMA2_PLUGINS = "\
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-mediaportal \
    ${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-extensions-foreca" , "", d)} \
    enigma2-plugin-extensions-dlnabrowser \
    ${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-systemplugins-videotune" , "", d)} \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    ${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-systemplugins-3gmodemmanager" , "", d)} \
    enigma2-plugin-systemplugins-positionersetup \
    ${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-systemplugins-micomupgrade" , "", d)} \
"
ENIGMA2_INI_PLUGINS = "\
    enigma2-plugin-extensions-inimytube \
    enigma2-plugin-extensions-infopanel \
"

ENIGMA2_USB_DRV = "\
    enigma2-plugin-drivers-dvb-usb-af9035 \
    enigma2-plugin-drivers-dvb-usb-dib0700 \
    enigma2-plugin-drivers-dvb-usb-af9015 \
    enigma2-plugin-drivers-dvb-usb-siano \
    enigma2-plugin-drivers-dvb-usb-dw2102 \
    enigma2-plugin-drivers-dvb-usb-as102 \
    enigma2-plugin-drivers-dvb-usb-dtt200u \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-drivers-dvb-usb-dib0700 \
    enigma2-plugin-drivers-dvb-usb-af9015 \
    enigma2-plugin-drivers-dvb-usb-siano \
    enigma2-plugin-drivers-dvb-usb-em28xx  \
    enigma2-plugin-drivers-dvb-usb-it913x \
    enigma2-plugin-drivers-dvb-usb-pctv452e \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    enigma2-plugin-drivers-network-usb-ath9k-htc \
    enigma2-plugin-drivers-network-usb-rt3070 \
    enigma2-plugin-drivers-network-usb-carl9170 \
    enigma2-plugin-drivers-network-usb-rt2500 \
    enigma2-plugin-drivers-network-usb-rt2800 \
    enigma2-plugin-drivers-network-usb-rtl8187 \
    enigma2-plugin-drivers-network-usb-zd1211rw \
    enigma2-plugin-drivers-network-usb-rtl8192cu \
    enigma2-plugin-drivers-network-usb-asix \
    enigma2-plugin-drivers-network-usb-ax88179-178a \
    enigma2-plugin-drivers-network-usb-r8712u \
    enigma2-plugin-drivers-network-usb-rt73 \
"


export IMAGE_BASENAME = "SWF"
IMAGE_LINGUAS = ""

inherit image

rootfs_postprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true

    cd $curdir
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

do_generate_nfo() {
    VER=`grep Version: "${IMAGE_ROOTFS}/var/lib/opkg/info/enigma2.control" | cut -b 10-26`
    echo "Enigma2: ${VER}" > ${NFO}
    echo "Machine: ${MACHINE}" >> ${NFO}
    DATE=`date +%Y-%m-%d' '%H':'%M`
    echo "Date: ${DATE}" >> ${NFO}
    echo "Issuer: mcron" >> ${NFO}
    echo "Link: http://sat-world-forum.com/ronny/images/${MACHINE}/online/${IMAGE_NAME}_usb.zip" >> ${NFO}
    if [ "${DESC}" != "" ]; then
       echo "Description: ${DESC}" >> ${NFO}
       echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
    fi
    MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}_usb.zip | cut -b 1-32`
    echo "MD5: ${MD5SUM}" >> ${NFO}
}

addtask generate_nfo after do_rootfs
