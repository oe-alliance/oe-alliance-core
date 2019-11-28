SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r16"

inherit packagegroup

DEPENDS = " \
	ca-certificates \
	flip \
	hddtemp \
	openatv-enigma2 \
	openatv-spinner \
	oe-alliance-base \
	packagegroup-base-smbfs-client \
	python-imaging \
	python-service-identity \
	rtmpdump \
	titan-netsurf \
	"
#	titan-gmediarender

RDEPENDS_${PN} = "\
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
	fuse-exfat \
	glibc-gconv-iso8859-15 \
	glib-networking \
	jpeg-tools \
	kernel-module-ftdi-sio \
	kernel-module-pl2303 \
	kernel-module-belkin-sa \
	kernel-module-keyspan \
	kernel-module-tun \
	libdreamdvd \
	libdvdcss \
	libusb1 \
	mc \
	minilocale \
	minidlna \
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
	packagegroup-base \
	packagegroup-core-boot \
	packagegroup-base-smbfs-client \
	packagegroup-base-smbfs-server \
   	packagegroup-base-smbfs-utils \
   	packagegroup-base-nfs \
	${@bb.utils.contains('MACHINE_FEATURES', 'emmc', 'partitions-by-name' , '', d)} \ 
	pngquant \
	procps \
	rtmpdump \
	samba \
	sdparm \
	smbclient \
	smbnetfs \
	titannit-bootlogo \
	titan-rarfs \
	tuxtxt-enigma2 \
	tzdata tzdata-europe tzdata-australia tzdata-asia tzdata-pacific tzdata-africa tzdata-americas \
	util-linux-blkid \
	util-linux-sfdisk \
	util-linux-fsck \
	volatile-media \
	vsftpd \
	wakelan \
	wget \
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
	${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "oe-alliance-wifi", d)} \
	oe-alliance-feeds-configs \
	openatv-version-info \
	\
	titan-autorestore \
 	titan-infobox \
	titan-rarfs \
	titan-fbread \
	titan-tuxtxt \
 	"

#	${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "", "oe-alliance-wifi", d)}
#
#	titan-gmediarender
#	titannit-version-info
#	kernel-module-usbserial
#	parted
#	fakelocale
#	portmap
#	portmap-utils
