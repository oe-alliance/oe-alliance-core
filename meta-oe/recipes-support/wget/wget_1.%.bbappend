FILESEXTRAPATHS_append := "${THISDIR}/wget"

SRC_URI_append = " \
           file://0002-Strip-long-version-output.patch \
          "

DEPENDS_remove = "gnutls"
DEPENDS_append = " openssl"
EXTRA_OECONF_remove = "--with-ssl=gnutls"
EXTRA_OECONF_append = " --with-ssl=openssl"

inherit binary-compress

FILES_COMPRESS = "${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "${bindir}/wget ${bindir}/wget.wget", "", d)}"
