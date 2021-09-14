RDEPENDS_${PN} += "glib-openssl"

CFLAGS_append_sh4 += "-std=gnu11"

ALLOW_EMPTY_${PN} = "1"
