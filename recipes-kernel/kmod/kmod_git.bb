DESCRIPTION = "kmod - handle kernel modules"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r21"
PACKAGES =+ "libkmod"

inherit autotools gitpkgv
 
PKGV = "${GITPKGVTAG}"
# SRCREV = "8885ced062131214448fae056ef453f094303805"
SRCREV = "abb910eb9db6b40900a64651a4d63a37edbcc739"
SRC_URI = "git://git.kernel.org/pub/scm/utils/kernel/kmod/kmod.git;protocol=git"
 
EXTRA_OECONF = "--disable-manpages"

S = "${WORKDIR}/git"

do_configure_prepend() {
	gtkdocize --docdir ${S}/libkmod/docs || touch ${S}/libkmod/docs/gtk-doc.make
}
