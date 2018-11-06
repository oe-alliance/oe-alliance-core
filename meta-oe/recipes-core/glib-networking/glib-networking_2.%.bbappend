# Just a comment line to avoid PAK archive (application/x-pak)
PACKAGECONFIG = "ca-certificates"
RDEPENDS_${PN} += "glib-openssl"
ALLOW_EMPTY_${PN} = "1"
