SUMMARY = "TitanNit Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

IMAGE_INSTALL = "\
	aio-grab \
	alsa-conf \
	alsa-utils \
	autofs \
	avahi-daemon \
	bash \
	curl \
	curlftpfs \
	djmount \
	dropbear \
	dvbsnoop \
	e2fsprogs-e2fsck \
	e2fsprogs-mke2fs \
	e2fsprogs-tune2fs \
	early-configure \
	ethtool \
	fakelocale \
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	fuse-exfat \
	glibc-gconv-iso8859-15 \
	glib-networking \
	kernel-module-usbserial \
	kernel-module-ftdi-sio \
	kernel-module-pl2303 \
	kernel-module-belkin-sa \
	kernel-module-keyspan \
	kernel-module-ipv6 \
	kernel-module-tun \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	kernel-module-ath9k-htc \
	kernel-module-rt2800usb \
	${@base_contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)} \
	rt3070 \
	rt8812au \
	rt8723a \
	libdreamdvd \
	libdvdcss \
	libusb1 \
	mc \
	minidlna \
	mjpegtools \
	module-init-tools-depmod \
	modutils-loadscript \
	mtd-utils \
	nfs-utils \
	nfs-utils-client \
	ntfs-3g \
	ntpdate \
	ofgwrite \
	openssl \
	openvpn \
	opkg \
	${@base_contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)} \
	packagegroup-core-boot \
	parted \
	pngquant \
	procps \
	portmap \
	portmap-utils \
	rtmpdump \
	samba \
	sambaserver \
	sdparm \
	smbclient \
	smbnetfs \
	strace \
	titannit-bootlogo \
	titannit-version-info \
	titan-gmediarender \
	titan-rarfs \
	tuxtxt-enigma2 \
	tzdata tzdata-europe tzdata-australia tzdata-asia tzdata-pacific tzdata-africa tzdata-americas \
	util-linux-blkid \
	util-linux-sfdisk \
	volatile-media \
	vsftpd \
	wakelan \
	wireless-tools \
	wpa-supplicant \
	\
	\	
	${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
	${@base_conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
	${@base_contains("MACHINE_FEATURES", "dvbc-only", "", "", d)} \
	${@base_contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
	${@base_contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
	${@base_contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
	${@base_contains("MACHINE_FEATURES", "singlecore", "", \
	" \
	", d)} \
	${@base_contains("TARGET_ARCH", "sh4", "alsa-utils-amixer-conf" , "", d)} \
	${@base_contains("TARGET_ARCH", "sh4", "libmmeimage " , "", d)} \
	titan-bin \
    "

#	${@base_contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
# disable for packagegroup-base
#	libcrypto-compat-0.9.7
#	libcrypto-compat-0.9.8
# disable for dm7200hd
#	kernel-module-rtl8192cu
# disabled building on svn
#	${@base_contains('MACHINE', 'vusolo2', 'titan-xbmc-helix', '', d)} \
#	${@base_contains('MACHINE', 'inihdp', 'titan-xbmc-nightly', '', d)} \
#    snes9x-sdl
#    libavahi-client
#    minidlna
#    ${@base_contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    ${@base_conditional('MACHINE', 'inihdp', '', 'titan-xbmc', d)} \
#    ${@base_contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    titan-bin
#    ${@base_contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
#    titan-plugins
#    enigma2-locale-meta test fpr glibc only

export IMAGE_BASENAME = "titannit-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

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
    VER=`grep Version: "${IMAGE_ROOTFS}/etc/version"`
    echo "TitanNit: ${VER}" > ${NFO}
    echo "Machine: ${MACHINE}" >> ${NFO}
    DATE=`date +%Y-%m-%d' '%H':'%M`
    echo "Date: ${DATE}" >> ${NFO}
    echo "Issuer: openATV" >> ${NFO}
    echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
    if [ "${DESC}" != "" ]; then
            echo "Description: ${DESC}" >> ${NFO}
            echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
    fi
    MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
    echo "MD5: ${MD5SUM}" >> ${NFO}
}

addtask generate_nfo after do_rootfs
