# Retain the selected V3D runtime even with framebuffer-only Enigma2 and no
# optional EGL applications installed. Older Edision HD models are unchanged.
RDEPENDS:${PN}:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-v3d', ' edision-libv3d', '', d)}"
PR:append = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-bcm-v3d', '.v3d1', '', d)}"
