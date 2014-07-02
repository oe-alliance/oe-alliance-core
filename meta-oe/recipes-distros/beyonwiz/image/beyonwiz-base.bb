SUMMARY = "Base packages required for Beyonwiz image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r4"

inherit packagegroup

RDEPENDS_${PN} = "\
    beyonwiz-enigma2 \
    beyonwiz-bootlogo \
    beyonwiz-feeds \    
    beyonwiz-version-info \    
    \
    python-gdata \
    minidlna djmount fuse-utils \
    task-base-smbfs \
    task-base-smbfs-client \
    ntfs-3g \
    hddtemp \
    rsync \
    busybox-cron \
    ${ENIGMA2_DVB_USB_DRV} \
    \
    oe-alliance-branding \
    avahi-daemon \
    dropbear \
    early-configure \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    fakelocale \
    libavahi-client \
    libcrypto-compat-0.9.7 \
    modutils-loadscript \
    ntpdate \
    opkg \
    sdparm \
    packagegroup-base \
    packagegroup-core-boot \
    tzdata \
    util-linux-sfdisk \
    util-linux-blkid \
    volatile-media \
    vsftpd \
    "

ENIGMA2_DVB_USB_DRV = "\
    enigma2-plugin-drivers-dvb-usb-dib0700 \
    enigma2-plugin-drivers-dvb-usb-af9015 \
    enigma2-plugin-drivers-dvb-usb-em28xx \
    enigma2-plugin-drivers-dvb-usb-dw2102 \
    enigma2-plugin-drivers-dvb-usb-it913x \
    enigma2-plugin-drivers-dvb-usb-pctv452e \
    enigma2-plugin-drivers-dvb-usb-dtt200u \
    enigma2-plugin-drivers-dvb-usb-af9035 \
    enigma2-plugin-drivers-usbserial \
    "
