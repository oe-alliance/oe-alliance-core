RDEPENDS_${PN} += "glib-openssl"

CFLAGS_append_sh4 += "-std=gnu99"

ALLOW_EMPTY_${PN} = "1"
