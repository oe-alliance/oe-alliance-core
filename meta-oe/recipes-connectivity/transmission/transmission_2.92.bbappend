SRC_URI += "\
	file://configure-kill-intl-check.patch \
	file://allow_the_rpc_server_to_listen_on_an_ipv6_address.patch \
	file://configure-allow-local-network.patch \
	"

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
