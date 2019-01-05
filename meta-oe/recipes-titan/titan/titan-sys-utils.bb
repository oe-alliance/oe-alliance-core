SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.0+gitr${GITPKGV}"
PV = "2.0+gitr${SRCPV}"
PR = "r3"

SRC_URI = "git://github.com/karelzak/util-linux.git;module=sys-utils;protocol=git;branch=master"

DEPENDS = " \
	"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = " \
	--disable-option-checking  \
	--disable-FEATURE       \
	--disable-dependency-tracking \
	--disable-silent-rules  \
	--disable-libtool-lock  \
	--disable-symvers       \
	--disable-largefile     \
	--disable-assert        \
	--disable-nls           \
	--disable-rpath         \
	--disable-all-programs  \
	--disable-tls           \
	--disable-widechar      \
	--disable-libuuid       \
	--disable-libblkid      \
	--disable-libmount      \
	--disable-libsmartcols  \
	--disable-libfdisk      \
	--disable-fdisks        \
	--disable-mount         \
	--disable-losetup       \
	--disable-zramctl       \
	--disable-fsck          \
	--disable-partx         \
	--disable-uuidd         \
	--disable-mountpoint    \
	--disable-fallocate     \
	--disable-unshare       \
	--disable-nsenter       \
	--disable-setpriv       \
	--disable-eject         \
	--disable-agetty        \
	--disable-plymouth_support \
	--disable-cramfs        \
	--disable-bfs           \
	--disable-minix         \
	--disable-fdformat      \
	--disable-hwclock       \
	--disable-lslogins      \
	--disable-wdctl         \
	--disable-cal           \
	--disable-logger        \
	--disable-switch_root   \
	\
	--enable-pivot_root     \
	\
	--disable-lsmem         \
	--disable-chmem         \
	--disable-ipcrm         \
	--disable-ipcs          \
	--disable-rfkill        \
	--disable-kill          \
	--disable-last          \
	--disable-utmpdump      \
	--disable-mesg          \
	--disable-raw           \
	--disable-rename        \
	--disable-chfn-chsh-password \
	--disable-chsh-only-listed \
	--disable-login         \
	--disable-nologin       \
	--disable-sulogin       \
	--disable-su            \
	--disable-runuser       \
	--disable-ul            \
	--disable-more          \
	--disable-setterm       \
	--disable-schedutils    \
	--disable-wall          \
	--disable-bash-completion \
	--disable-pylibmount    \
	--disable-pg-bell       \
	--disable-use-tty-group \
	--disable-makeinstall-chown \
	--disable-makeinstall-setuid \
	--disable-colors-default \
"

do_compile() {
	cd ${WORKDIR}/build
	make pivot_root
}

FILES_${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 pivot_root ${D}/sbin/pivot_root
}
do_install[vardepsexclude] += "DATETIME"

