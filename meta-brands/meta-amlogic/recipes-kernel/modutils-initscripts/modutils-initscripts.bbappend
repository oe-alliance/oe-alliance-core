# The GTX kernel checks module versions. Stripping them makes even the
# matching decoder modules fail to load before showiframe and Enigma2.
do_install:append:gtxcombo() {
    sed -i 's/^LOAD_MODULE="modprobe --force-modversion"$/LOAD_MODULE=modprobe/' ${D}${sysconfdir}/init.d/modutils.sh
    grep -qx 'LOAD_MODULE=modprobe' ${D}${sysconfdir}/init.d/modutils.sh
}
