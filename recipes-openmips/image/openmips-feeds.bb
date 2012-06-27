DESCRIPTION = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PV = "1.0"
PR = "r0"

inherit task

RRECOMMENDS = "\
	dvb-usb-drivers-meta \
	channelsettings-enigma2-meta \
	picons-enigma2-meta \
	task-openplugins \
	\
	enigma2-skins \
	enigma2-plugins \
	enigma2-pliplugins \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-tuxterm \
	enigma2-plugin-extensions-webinterface-old \
	enigma2-plugin-security-firewall \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugin-vix-magic-sd \
	enigma2-plugin-vix-magic-hd \
	enigma2-plugin-vix-magic-hd-lite \
	enigma2-plugin-vix-magic-hd-night \
	enigma2-plugin-vix-day-hd \
	enigma2-plugin-vix-night-hd \
	enigma2-plugin-vix-vixbmc-slim-hd \
	enigma2-plugin-vix-vixbmc-night-hd \
	enigma2-plugin-extensions-openairplay \
	enigma2-plugin-extensions-et-livestream \
	enigma2-plugin-extensions-mediatomb \
	${@base_contains("MACHINE_FEATURES", "fullgraphiclcd", "lcdpicons-enigma2-meta" , "", d)} \
	\
	ctorrent \
	djmount \
	dvbsnoop \
	dvdfs \
	htop \
	hddtemp \
	mc \
	minidlna \
	mpd \
	nano \
	ntfs-3g \
	openresolv \
	openssh \
	openvpn \
	parted \
	procps \
	rsync \
	sabnzbd \
	samba \
	smartmontools \
	strace \
	tcpdump \
	ushare \
	vim \
	wakelan \
	zeroconf \
	"
