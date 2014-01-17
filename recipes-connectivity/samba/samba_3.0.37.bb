require recipes-connectivity/samba/samba.inc
require recipes-connectivity/samba/samba-basic.inc
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://../COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

PR = "r13"

SRC_URI += "file://configure.patch \
	file://kernel-oplocks.patch \
	file://0001-s3-schannel-client-Push-the-domain-and-netbios-name-.patch \
	file://samba-3.0-CVE-2012-0870.patch \
	file://samba-3.0.37-CVE-2012-1182.patch \
	file://01samba-kill \
	file://01samba-start \
	"

SRC_URI_append_linux-uclibc        = "file://uclibc-strlcpy-strlcat.patch"
SRC_URI_append_linux-uclibceabi = "file://uclibc-strlcpy-strlcat.patch"

SRC_URI[md5sum] = "11ed2bfef4090bd5736b194b43f67289"
SRC_URI[sha256sum] = "bb67c0e13d4ccbd84b9200c8739393fdd9b3145b5aad216934dc670f0fcea266"

EXTRA_OECONF += " \
	SMB_BUILD_CC_NEGATIVE_ENUM_VALUES=yes \
	samba_cv_LINUX_LFS_SUPPORT=yes \
	samba_cv_HAVE_OFF64_T=yes \
	samba_cv_have_longlong=yes \
	samba_cv_HAVE_UNSIGNED_CHAR=yes \
	samba_cv_HAVE_GETTIMEOFDAY_TZ=yes \
	samba_cv_HAVE_C99_VSNPRINTF=yes \
	samba_cv_HAVE_BROKEN_READDIR=no \
	samba_cv_HAVE_IFACE_IFCONF=yes \
	"

PACKAGES =+ "smbfs smbfs-doc sambaserver libpopt libtalloc"

FILES_smbfs = "${bindir}/smbmount ${bindir}/smbumount ${bindir}/smbmnt ${base_sbindir}/mount.smbfs ${base_sbindir}/mount.smb"
FILES_smbfs-doc = "${mandir}/man8/smbmount.8 ${mandir}/man8/smbumount.8 ${mandir}/man8/smbmnt.8"
FILES_sambaserver = "${sbindir}/smbd ${sbindir}/nmbd ${libdir}/charset/*.so ${libdir}/*.dat \
	${sysconfdir}/samba/smb.conf ${sysconfdir}/samba/private \
	${sysconfdir}/network/if-up.d/01samba-start ${sysconfdir}/network/if-down.d/01samba-kill"
FILES_libpopt = "${libdir}/libpopt.so.*"
FILES_libtalloc = "${libdir}/libtalloc.so.*"

CONFFILES_${PN} = ""
CONFFILES_sambaserver = "${sysconfdir}/samba/smb.conf"

do_install_prepend() {
	install -c -m 644 ${WORKDIR}/smb.conf ../examples/smb.conf.default
}

do_install_append() {
	install -d ${D}${sysconfdir}/samba/private
	install -d ${D}${sysconfdir}/network/if-down.d
	install -m 0755 ${WORKDIR}/01samba-kill ${D}${sysconfdir}/network/if-down.d
	install -d ${D}${sysconfdir}/network/if-up.d
	install -m 0755 ${WORKDIR}/01samba-start ${D}${sysconfdir}/network/if-up.d
}
