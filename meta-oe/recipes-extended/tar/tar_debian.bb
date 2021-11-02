#
# base recipe: meta/recipes-extended/tar/tar_1.30.bb
# base branch: master
# base commit: 63a4ff7cf5f7d1671ab85800bc2212dd9cd9748d
#

SUMMARY = "GNU file archiving program"
DESCRIPTION = "GNU tar saves many files together into a single tape \
or disk archive, and can restore individual files from the archive."
HOMEPAGE = "http://www.gnu.org/software/tar/"


inherit debian-package
inherit upx-compress
require tar.inc

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

FILESPATH:append = ":${COREBASE}/meta/recipes-extended/tar/tar"
SRC_URI += "file://musl_dirent.patch"

inherit autotools gettext texinfo

PACKAGECONFIG ??= ""
PACKAGECONFIG:append:class-target = " ${@bb.utils.filter('DISTRO_FEATURES', 'acl', d)}"

PACKAGECONFIG[acl] = "--with-posix-acls, --without-posix-acls, acl,"

EXTRA_OECONF += "DEFAULT_RMT_DIR=${base_sbindir}"

# Let aclocal use the relative path for the m4 file rather than the
# absolute since tar has a lot of m4 files, otherwise there might
# be an "Argument list too long" error when it is built in a long/deep
# directory.
acpaths = "-I ./m4"

do_install:append () {
	ln -s tar ${D}${bindir}/gtar
}

do_install:append:class-target() {
	if [ "${base_bindir}" != "${bindir}" ]; then
		install -d ${D}${base_bindir}
		mv ${D}${bindir}/tar ${D}${base_bindir}/tar
		mv ${D}${bindir}/gtar ${D}${base_bindir}/gtar
		rmdir ${D}${bindir}/
	fi
}

do_install:append:libc-musl() {
	rm -f ${D}${libdir}/charset.alias
	rmdir ${D}${libdir}
}

PACKAGES =+ "${PN}-rmt"
PACKAGE_NO_LOCALE = "1"

FILES:${PN}-rmt = "${base_sbindir}/rmt*"

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"

ALTERNATIVE:${PN} = "tar"
ALTERNATIVE:${PN}-rmt = "rmt"
ALTERNATIVE:${PN}:class-nativesdk = ""
ALTERNATIVE:${PN}-rmt:class-nativesdk = ""

ALTERNATIVE_LINK_NAME[tar] = "${base_bindir}/tar"
ALTERNATIVE_LINK_NAME[rmt] = "${base_sbindir}/rmt"

PROVIDES:append:class-native = " tar-replacement-native"
NATIVE_PACKAGE_PATH_SUFFIX = "/${PN}"

BBCLASSEXTEND = "native nativesdk"
