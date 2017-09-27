SUMMARY = "GLib networking for tls using openssl"
DESCRIPTION = "This is a fork of glib-networking providing only tls support using openssl. As a side point it fully supports Windows."
HOMEPAGE = "http://git.gnome.org/browse/glib-openssl/"
BUGTRACKER = "http://bugzilla.gnome.org"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5f30f0716dfdd0d91eb439ebec522ec2"

SECTION = "libs"
DEPENDS = "glib-2.0 intltool-native openssl"

SRC_URI[archive.md5sum] = "bd4746fcd00bf338af538bd765413a5b"
SRC_URI[archive.sha256sum] = "0211c118b86aec228d2b7d2606bba9637d5bb5d60694cc7ccb6d2920f02866bc"

inherit gnomebase gettext upstream-version-is-even gio-module-cache

FILES_${PN} += "${libdir}/gio/modules/libgio*.so ${datadir}/dbus-1/services/"
FILES_${PN}-dev += "${libdir}/gio/modules/libgio*.la"
FILES_${PN}-staticdev += "${libdir}/gio/modules/libgio*.a"
