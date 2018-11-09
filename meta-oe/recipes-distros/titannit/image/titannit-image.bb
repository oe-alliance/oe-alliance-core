SUMMARY = "TitanNit Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit team"

require conf/license/license-gplv2.inc

#PV = "${IMAGE_VERSION}"
#PR = "r${DATETIME}"
#PACKAGE_ARCH = "${MACHINE_ARCH}"
PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

do_rootfs[deptask] = "do_rm_work"

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
	exteplayer3 \
	fakelocale \
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	fuse-exfat \
	glibc-gconv-iso8859-15 \
	glib-networking \
	kernel-module-ftdi-sio \
	kernel-module-pl2303 \
	kernel-module-belkin-sa \
	kernel-module-keyspan \
	kernel-module-tun \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	${@bb.utils.contains('MACHINE', 'disabled-ath9k', '', 'kernel-module-ath9k-htc', d)} \
	kernel-module-rt2800usb \
	${@bb.utils.contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)} \
	rt3070 \
	rt8812au \
	rt8723a \
	${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
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
	${@bb.utils.contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)} \
	packagegroup-core-boot \
	packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \    
	pngquant \
	procps \
	rtmpdump \
	samba \
	sdparm \
	smbclient \
	smbnetfs \
	strace \
	titannit-bootlogo \
	titannit-version-info \
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
	${@oe.utils.conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
	" \
	", d)} \
	${@bb.utils.contains("TARGET_ARCH", "sh4", "alsa-utils-amixer-conf" , "", d)} \
	${@bb.utils.contains("TARGET_ARCH", "sh4", "libmmeimage " , "", d)} \
	titan-libeplayer3 \
    "

#	kernel-module-usbserial
#	${@bb.utils.contains('MACHINE', 'disabled-ipv6', '', 'kernel-module-ipv6', d)}
#	parted
#	titan-bin
#	titan-gmediarender
#	portmap
#	portmap-utils
#	sambaserver
#
#    python
#    python-codecs
#    python-compression
#    python-core
#    python-crypt
#    python-fcntl
#    python-lang
#    python-netclient
#    python-netserver
#    python-pickle
#    python-re
#    python-shell
#    python-threading
#    python-twisted-core
#    python-twisted-web
#    python-utf8-hack
#    python-xml
#    python-zlib
#    python-zopeinterface
#    python-email
#    python-mime
#    python-pyusb
#    python-subprocess
#    python-process
#    python-imaging
#	${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
# disable for packagegroup-base
#	libcrypto-compat-0.9.7
#	libcrypto-compat-0.9.8
# disable for dm7200hd
#	kernel-module-rtl8192cu
# disabled building on svn
#	${@bb.utils.contains('MACHINE', 'vusolo2', 'titan-xbmc-helix', '', d)} \
#	${@bb.utils.contains('MACHINE', 'inihdp', 'titan-xbmc-nightly', '', d)} \
#    snes9x-sdl
#    libavahi-client
#    minidlna
#    ${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    ${@oe.utils.conditional('MACHINE', 'inihdp', '', 'titan-xbmc', d)} \
#    ${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    titan-bin
#    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
#    titan-plugins
#    enigma2-locale-meta test fpr glibc only




IMAGE_INSTALL1 = "openatv-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-settings-defaultsat", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-cl-svr", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    "
# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

export IMAGE_BASENAME = "openatv-image"
# 64 or 128MB of flash: No language files, above: German and French
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de fr-fr", d)}"

# Add more languages for 512 or more MB of flash:
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

IMAGE_FEATURES += "package-management"

INHIBIT_DEFAULT_DEPS = "1"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete

