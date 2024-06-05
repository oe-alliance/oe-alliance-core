SUMMARY = "lightweight DLNA/UPnP-AV server targeted at embedded systems"
HOMEPAGE = "http://sourceforge.net/projects/minidlna/"
SECTION = "network"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b1a795ac1a06805cf8fd74920bc46b5c"
DEPENDS = "libexif libjpeg-turbo libid3tag flac libvorbis sqlite3 ffmpeg util-linux virtual/libiconv"

# NLS causes autoconfigure problems - we don't need the extra languages anyway, so disable nls
EXTRA_OECONF:append = " --disable-nls "


SRC_URI = "https://mirrors.omniosce.org/minidlna//minidlna-${PV}.tar.gz  \
		file://0001_default_sqlite_caches.diff \
                file://0002-fix-build-with-fno-common.patch \
                file://0003-metadata-add-libavformat-57-plus-compatibility.patch \
		file://minidlna.conf \
		file://init \
"

SRC_URI[md5sum] = "1970e553a1eb8a3e7e302e2ce292cbc4"
SRC_URI[sha256sum] = "8477ad0416bb2af5cd8da6dde6c07ffe1a413492b7fe40a362bc8587be15ab9b"

S = "${WORKDIR}/${BPN}-${PV}"

inherit autotools-brokensep gettext update-rc.d

PACKAGES =+ "${PN}-utils"

FILES:${PN}-utils = "${bindir}/test*"

CONFFILES:${PN} = "${sysconfdir}/minidlna.conf"

INITSCRIPT_NAME = "minidlna"

do_install:append() {
	install -d ${D}${sysconfdir} ${D}${sysconfdir}/init.d/
	install -m 644 ${UNPACKDIR}/minidlna.conf ${D}${sysconfdir}/minidlna.conf
	install -m 755 ${UNPACKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}

pkg_preinst:${PN} () {
	if [ -f /etc/minidlna.conf ];then
		mv /etc/minidlna.conf /etc/minidlna.conf.orig
	fi
}

pkg_postinst:${PN} () {
if [ -f /etc/minidlna.conf.orig ];then
		mv /etc/minidlna.conf.orig /etc/minidlna.conf
	fi
}

PACKAGE_NO_LOCALE = "1"
