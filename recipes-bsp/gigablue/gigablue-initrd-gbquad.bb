SRCDATE = "20140410"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "d2f40f660866a975530b74c7e0a1d61f"
SRC_URI[sha256sum] = "b88a0273ef62586fd0c35ed4721564a37ed633be3531ea626464db087f18e14d"
