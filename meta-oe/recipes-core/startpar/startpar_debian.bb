SUMMARY = "run processes in parallel and multiplex their output"
DESCRIPTION = "Used by the sysv-rc boot system executor to run init.d \
scripts in parallel while making sure the script output is not completely \
messed up."
HOMEPAGE = "http://savannah.nongnu.org/projects/sysvinit"

require conf/license/license-gplv2.inc

PR = "r0"
inherit debian-package
PV = "0.59"

SRC_URI = " \
           http://http.debian.net/debian/pool/main/s/startpar/startpar_0.59.orig.tar.bz2;name=orig \
           http://http.debian.net/debian/pool/main/s/startpar/startpar_0.59-4.debian.tar.xz;name=debian;subdir=${PN}-${PV} \
           file://Makefile.patch \
          "

SRC_URI[orig.md5sum] = "864700350e068ad7d87e094fdd58b651"
SRC_URI[orig.sha256sum] = "30a30c8d27a694e3743c1839fb6f60563b2882cddf0e61c698120c4cbde1d7b9"
SRC_URI[debian.md5sum] = "b34a8f00502ebe4fae462d0bee51eafa"
SRC_URI[debian.sha256sum] = "889a1bbb2441962df706ee5ecb30d74376750f1cc4e5190017f3871df28c3793"

DEBIAN_UNPACK_DIR="${WORKDIR}/${PN}-${PV}"

do_install () {
        cd ${S}/debian
	cp changelog changelog.Debian
        gzip changelog.Debian

	cd ${S}
	install -d ${D}/${base_libdir}/startpar
	install -m 755 ${S}/startpar ${D}/${base_libdir}/startpar/startpar

	install -d ${D}/usr/share/doc/startpar
	install -m 644 ${S}/debian/copyright ${D}/usr/share/doc/startpar
	install -m 644 ${S}/debian/changelog.Debian.gz ${D}/usr/share/doc/startpar

	install -d ${D}/usr/share/man/man8
        gzip startpar.8
	install -m 644 ${S}/startpar.8.gz ${D}/usr/share/man/man8
}

do_rm_work() {
}

FILES_${PN} = "${sysconfdir} ${base_libdir}"
