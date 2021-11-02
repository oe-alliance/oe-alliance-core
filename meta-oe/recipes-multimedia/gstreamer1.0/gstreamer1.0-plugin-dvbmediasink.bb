DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base libdca"

GSTVERSION = "1.0"

SRC_URI = "git://github.com/christophecvr/gstreamer1.0-plugin-multibox-dvbmediasink;branch=openatv-dev;protocol=https"

S = "${WORKDIR}/git"

inherit gitpkgv

PV = "${GSTVERSION}+git${SRCPV}"
PKGV = "${GSTVERSION}+git${GITPKGV}"

# added to have al m4 macro's into build when using bitbake with -b option.
# Then proceeding to full image build or at least package build with recipes parsing is not needed.
do_configure_prepend() {
    ln -sf ${STAGING_DATADIR_NATIVE}/aclocal/*.m4 ${S}/m4/
}

do_configure_prepend() {
    sed -i 's/AC_INIT.*$/AC_INIT(gst-plugin-dvbmediasink, 1.0.0, @pli4)/' ${S}/configure.ac
    sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign])/' ${S}/configure.ac
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-${GSTVERSION}/*.so* ${sysconfdir}/gstreamer/aactranscode"
FILES_${PN}-dev += "${libdir}/gstreamer-${GSTVERSION}/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-${GSTVERSION}/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-${GSTVERSION}/.debug"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG} --with-gstversion=${GSTVERSION} --with-machine=${MACHINE}"

pkg_preinst_${PN}_prepend () {
	if [ -d "/.cache/gstreamer-1.0" ]
	then
		rm -rf "/.cache/gstreamer-1.0"
	fi
	if [ -d "/home/root/.cache/gstreamer-1.0" ]
	then
		rm -rf "/home/root/.cache/gstreamer-1.0"
	fi
}
