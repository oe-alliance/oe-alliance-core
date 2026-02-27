FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

inherit gitpkgv

SRC_URI = "git://github.com/MusicPlayerDaemon/MPD;branch=master;protocol=https \
           file://mpd.conf.in \
           file://mpd.init \
           "

SRCREV = "94a02d4011f9f103f7639f6e01d3df1792f42e0a"

PV = "0.24.8+git"
PKGV = "0.24.8+git${GITPKGV}"

do_install:append() {
	install -d ${D}${localstatedir}/lib/mpd/playlists
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${UNPACKDIR}/mpd.init ${D}${sysconfdir}/init.d/mpd
}
