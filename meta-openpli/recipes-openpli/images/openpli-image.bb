require conf/license/openpli-gplv2.inc

inherit task image

IMAGE_INSTALL = " \
	avahi-daemon \
	openpli-bootlogo \
	distro-feed-configs \
	early-configure \
	fakelocale \
	opkg \
	vsftpd \
	sambaserver \
	sdparm \
	tuxbox-common \
	tzdata \
	util-linux-sfdisk \
	nfs-utils-client \
	cifs \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	task-base \
	task-core-boot \
	volatile-media \
	tuxbox-links \
	"

OPTIONAL_PACKAGES ?= ""
OPTIONAL_PACKAGES += " \
	openvpn \
	gdb strace \
	procps \
	openresolv \
	tcpdump \
	ntp \
	samba \
	openssh \
	ctorrent \
	sabnzbd \
	wakelan \
	inadyn-mt \
	cups \
	vim joe \
	mc \
	smartmontools \
	hdparm \
	rsync \
	ntfs-3g \
	mpd \
	parted \
	zeroconf \
	"

export IMAGE_BASENAME = "openpli"
IMAGE_LINGUAS = ""
