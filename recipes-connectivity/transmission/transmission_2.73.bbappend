SRC_URI += "file://configure-kill-intl-check.patch"

DEPENDS = "gnutls openssl libtool intltool-native curl glib-2.0-native libevent"

OE_EXTRACONF = "\
	--disable-gtk --without-gtk \
	--disable-nls --without-nls \
	--disable-cli \
	--disable-mac \
	--disable-wx \
	--disable-beos \
	--enable-lightweight \
	--enable-daemon \
	CPPFLAGS=-DTR_EMBEDDED \
	"

