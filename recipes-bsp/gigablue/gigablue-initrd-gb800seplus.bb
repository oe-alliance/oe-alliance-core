SRCDATE = "20140413"

require gigablue-initrd.inc

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "b7e1b3827033691c1981111c216c5cf8"
SRC_URI[sha256sum] = "ff47da7a2fdcf800a311f013e13b30c744caed777477dfee193842cd002fa5e4"
