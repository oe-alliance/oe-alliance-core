SUMMARY = "GLib networking for tls using openssl"
DESCRIPTION = "This is a fork of glib-networking providing only tls support using openssl. As a side point it fully supports Windows."
HOMEPAGE = "http://git.gnome.org/browse/glib-openssl/"
BUGTRACKER = "http://bugzilla.gnome.org"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5f30f0716dfdd0d91eb439ebec522ec2"

SECTION = "libs"
DEPENDS = "glib-2.0 intltool-native openssl"

SRC_URI_append = " \
    file://Find_CA_bundle_file_in_configure.patch \
    file://OpenSSL_Support_SNI_TLS_Extension.patch \
"

SRC_URI[archive.md5sum] = "de9f89e0e7d9aa973e6edffe78b82ce8"
SRC_URI[archive.sha256sum] = "23203c8f83e9442c51aeff75959470531135eb3872b638791de6a6f7fee65a9b"

inherit gnomebase gettext upstream-version-is-even gio-module-cache

FILES_${PN} += "${libdir}/gio/modules/libgio*.so ${datadir}/dbus-1/services/"
FILES_${PN}-dev += "${libdir}/gio/modules/libgio*.la"
FILES_${PN}-staticdev += "${libdir}/gio/modules/libgio*.a"
