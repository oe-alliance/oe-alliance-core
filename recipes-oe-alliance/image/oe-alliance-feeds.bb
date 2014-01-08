DESCRIPTION = "Merge machine and distro options to create a oe-allinace enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit task

PV = "1.0"
PR = "r43"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "enigma2-plugin-drivers-usbserial"
RECOMMENDS = "enigma2-plugin-extensions-et-livestream"

RRECOMMENDS = "\
	bootlogos-enigma2-meta \
	dvb-usb-drivers-meta \
	network-usb-drivers-meta \
	channelsettings-enigma2-meta \
	picons-enigma2-meta \
	task-openplugins \
	meta-enigma2-dvdburn \
	\
	enigma2-plugins \
	enigma2-plugin-drivers-ntfs-3g \
	enigma2-plugin-drivers-exfat \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-tuxterm \
	enigma2-plugin-security-firewall \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugin-extensions-openairplay \
	${@base_contains("TARGET_ARCH", "mipsel", "enigma2-plugin-extensions-et-livestream" , "", d)} \
	enigma2-plugin-extensions-mediatomb \
	enigma2-plugin-extensions-dreamplex \
	enigma2-plugin-extensions-iptvlistupdater \
	enigma2-plugin-extensions-et-portal \
	enigma2-plugin-codec-audio-apple-lossless-alac \
	enigma2-plugin-extensions-moviearchiver \
	\
	${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "lcdpicons-enigma2-meta" , "", d)} \
	\
	autofs \
	autossh \
	avahi-ui \
	ctorrent \
	cups \
	djmount \
	dosfstools \
	dvbsnoop \
	dvdfs \
	${@base_contains("MACHINE_FEATURES", "legacykernel", "" , "evtest", d)} \
	exfat-utils \
	fuse-exfat \
	gdb \
	hddtemp \
	hdparm \
	htop \
	inadyn-mt \
	iperf \
	joe \
	mc \
	minidlna \
	mpd \
	mtd-utils \
	nano \
	net-snmp \
	ntfs-3g \
	ntp \
	openresolv \
	openssh \
	openvpn \
	parted \
	procps \
	pyload \
	python-circuits python-circuits-bricks python-cocy python-mechanize \
	rsync \
	rtorrent \
	sabnzbd \
	samba \
	smartmontools \
	smbnetfs \
	sshpass \
	strace \
	tcpdump \
	transmission \
	ushare \
	vim \
	wakelan \
	xfsprogs \
	zeroconf \
	ofgwrite \
	gst-ffmpeg \
	idle3-tools \
	rtmpdump \
	"

RRECOMMENDS_append_vuuno = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_append_vuultimo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_append_vusolo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_append_vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_append_vuduo = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_append_vuduo2 = "enigma2-plugin-extensions-hbbtv"
