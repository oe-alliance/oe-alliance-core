PR = "r0"

DEPENDS = "fuse"
RDEPENDS_${PN} = "fuse"

inherit scons

require conf/license/license-gplv2.inc

SRC_URI = "http://exfat.googlecode.com/files/${PN}-${PV}.tar.gz"

scons_do_compile_prepend() {
	export CC='${STAGING_BINDIR_NATIVE}/${MULTIMACH_HOST_SYS}/${CC}'
}

do_install() {
	install -d ${D}/${sbindir}
	install -m 0755 ${S}/fuse/mount.exfat-fuse ${D}/${sbindir}/mount.exfat-fuse
}

SRC_URI[md5sum] = "59ff35d4ec8e53a79359edc53a6ab534"
SRC_URI[sha256sum] = "ecae22203461e508d53389617c17d22f68c1b565c700d7663ce67bc4a710e903"

