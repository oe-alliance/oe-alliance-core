SUMMARY = "GLib networking for tls using openssl"
DESCRIPTION = "This is a fork of glib-networking providing only tls support using openssl. As a side point it fully supports Windows."
HOMEPAGE = "http://git.gnome.org/browse/glib-openssl/"
BUGTRACKER = "http://bugzilla.gnome.org"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5f30f0716dfdd0d91eb439ebec522ec2"

SECTION = "libs"
DEPENDS = "glib-2.0 intltool-native openssl"

SRC_URI[archive.md5sum] = "db7ae779bbd45c2043186fdba08764b0"
SRC_URI[archive.sha256sum] = "869f08e4e9a719c1df411c2fb5554400f6b24a9db0cb94c4359db8dad18d185f"

inherit gnomebase gettext upstream-version-is-even gio-module-cache

EXTRA_OECONF = " --with-ca-certificates=/etc/ssl/certs/ca-certificates.crt "

FILES_${PN} += "${libdir}/gio/modules/libgio*.so ${datadir}/dbus-1/services/"
FILES_${PN}-dev += "${libdir}/gio/modules/libgio*.la"
FILES_${PN}-staticdev += "${libdir}/gio/modules/libgio*.a"
