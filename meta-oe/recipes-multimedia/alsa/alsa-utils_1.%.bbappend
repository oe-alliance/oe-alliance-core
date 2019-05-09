FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://add-SIGUSR2-signal-handler.patch \
"

# NLS causes autoconfigure problems - we don't need the extra languages anyway, so disable nls

EXTRA_OECONF_append = " --disable-nls "
