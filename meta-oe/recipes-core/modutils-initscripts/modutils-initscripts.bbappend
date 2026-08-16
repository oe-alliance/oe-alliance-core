PR = "r8"

MODUTILS_LOAD_MODULE ?= "modprobe --force-modversion"
MODUTILS_START_PRIORITY ?= "04"

do_install:append () {
	sed -i 's!LOAD_MODULE=modprobe!LOAD_MODULE="${MODUTILS_LOAD_MODULE}"!g' ${D}${sysconfdir}/init.d/modutils.sh
}

INITSCRIPT_NAME = "modutils.sh"
INITSCRIPT_PARAMS = "start ${MODUTILS_START_PRIORITY} S ."
