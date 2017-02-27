# NLS causes autoconfigure problems - we don't need the extra languages anyway, so disable nls

EXTRA_OECONF_append = " --disable-nls "
