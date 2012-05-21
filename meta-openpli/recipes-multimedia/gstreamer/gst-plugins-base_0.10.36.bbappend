DEPENDS += "cdparanoia orc orc-native"
PRINC = "1"

EXTRA_OECONF += "--enable-orc"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
