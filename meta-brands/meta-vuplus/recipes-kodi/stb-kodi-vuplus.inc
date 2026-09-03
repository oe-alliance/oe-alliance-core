# VU+ MIPSel uses the machine-specific libvupl package as its native EGL
# platform.  Its dependency chain also supplies the matching EGL/GLES/NXPL
# libraries, so a standalone Kodi IPK installation must pull it in explicitly.
RDEPENDS:${PN}:append = "${@' libvupl-${MACHINE}' if d.getVar('TARGET_ARCH') == 'mipsel' else ''}"
PR:append = "${@'.2' if d.getVar('TARGET_ARCH') == 'mipsel' else ''}"

INSANE_SKIP:${PN} += "file-rdeps"
