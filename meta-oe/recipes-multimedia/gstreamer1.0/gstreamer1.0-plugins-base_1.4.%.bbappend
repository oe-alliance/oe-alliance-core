FILESEXTRAPATHS_prepend := "${THISDIR}:"
PACKAGECONFIG[cdparanoia]     = "--enable-cdparanoia,--disable-cdparanoia,cdparanoia"
PACKAGECONFIG += "cdparanoia"
EXTRA_OECONF := "${@bb.data.getVar('EXTRA_OECONF',d,1).replace('--disable-cdparanoia', '--enable-cdparanoia')}"

require mips-only.inc
