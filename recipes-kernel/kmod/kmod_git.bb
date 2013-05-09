DESCRIPTION = "kmod - handle kernel modules"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r5"

inherit autotools gitpkgv

PKGV = "${GITPKGVTAG}"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git.profusion.mobi/users/lfelipe/kmod.git"

EXTRA_OECONF = "\
  --disable-manpages \
"

S = "${WORKDIR}/git"

do_configure_prepend() {
	gtkdocize --docdir ${S}/libkmod/docs || touch ${S}/libkmod/docs/gtk-doc.make
}

PACKAGES =+ "libkmod"
FILES_libkmod = "${libdir}/lib*${SOLIBS}"

