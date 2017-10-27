inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "openvpn"
INITSCRIPT_PARAMS_${PN} = "defaults"

