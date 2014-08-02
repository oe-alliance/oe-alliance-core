SUMMARY = "OpenHDF bootlogo"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "OpenHDF Team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

PV = "4.1"
PR = "r1"

S = "${WORKDIR}"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 06 S ."

inherit update-rc.d

SRC_URI = "file://bootlogo.mvi file://radio.mvi file://bootlogo.sh ${@base_contains("MACHINE_FEATURES", "bootsplash", "file://splash.bin" , "", d)} "
SRC_URI_append_gb800ue = "file://lcdsplash.bin"
SRC_URI_append_gbquad = "file://lcdsplash.bin"
SRC_URI_append_gb800ueplus = "file://lcdsplash.bin"
SRC_URI_append_gbquadplus = "file://lcdsplash400.bin file://lcdwaitkey400.bin file://lcdwarning400.bin"
SRC_URI_append_dags3 = "file://splash1.bmp file://splash1_os1.bmp file://splash1_os2.bmp file://splash2.bmp file://splash3.bmp"

BINARY_VERSION = "1.3"

SRC_URI[dm800.md5sum] = "0aacd07cc4d19b388c6441b007e3525a"
SRC_URI[dm800.sha256sum] = "978a7c50fd0c963013477b5ba08462b35597ea130ae428c828bfcbb5c7cf4cac"
SRC_URI[dm8000.md5sum] = "1b63ac7e2bd5c0db0748606acc310d47"
SRC_URI[dm8000.sha256sum] = "91e4402190fe88cf394ae780141d968a1ebecd8db7b23c1f0ca3f4bfa9c9512a"
SRC_URI[dm800se.md5sum] = "3413a894a3d77e02cae34b96d302817d"
SRC_URI[dm800se.sha256sum] = "8a283442c231e82ee1a2093e53dc5bf52c478e12d22c79af7e7026b52711fc9c"
SRC_URI[dm500hd.md5sum] = "b9ada70304ca1f9a8e36a55bd38834c6"
SRC_URI[dm500hd.sha256sum] = "d4b0f650711d5d6fdecb42efe9e13987ef803cba829d0950e899608a784ae3b2"
SRC_URI[dm7020hd.md5sum] = "f8e423dbf7661367659fa86a68b74bc4"
SRC_URI[dm7020hd.sha256sum] = "118d7bb57c4b41dd45c7bdd9a056a0745454f42092692fb4309997e035eb6908"

FILES_${PN} = "/boot /usr/share /etc/init.d"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    ln -sf /usr/share/bootlogo.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/radio.mvi
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

inherit deploy
do_deploy() {
    if [ -e splash.bin ]; then
        install -m 0644 splash.bin ${DEPLOYDIR}/${BOOTLOGO_FILENAME}
    fi
    if [ -e lcdsplash.bin ]; then
        install -m 0644 lcdsplash.bin ${DEPLOYDIR}/lcdsplash.bin
    fi
    if [ -e lcdsplash400.bin ]; then
        install -m 0644 lcdsplash400.bin ${DEPLOYDIR}/lcdsplash.bin
    fi
    if [ -e splash1_os1.bmp ]; then
        install -m 0644 splash1_os1.bmp ${DEPLOYDIR}/splash1_os1.bmp
    fi
    if [ -e splash1_os2.bmp ]; then
        install -m 0644 splash1_os2.bmp ${DEPLOYDIR}/splash1_os2.bmp
    fi
	if [ -e splash1.bmp ]; then
        install -m 0644 splash1.bmp ${DEPLOYDIR}/splash1.bmp
    fi
    if [ -e splash2.bmp ]; then
        install -m 0644 splash2.bmp ${DEPLOYDIR}/splash2.bmp
    fi
    if [ -e splash3.bmp ]; then
        install -m 0644 splash3.bmp ${DEPLOYDIR}/splash3.bmp
    fi
}

addtask deploy before do_build after do_install

pkg_preinst_${PN}() {
    if grep dm /etc/hostname > /dev/null ; then
        if [ -z "$D" ]
        then
            if mountpoint -q /boot
            then
                mount -o remount,rw,compr=none /boot
            else
                mount -t jffs2 -o rw,compr=none mtd:'boot partition' /boot
            fi
        fi
    fi
}

pkg_postinst_${PN}() {
    if grep dm /etc/hostname > /dev/null ; then
        if [ -z "$D" ]
        then
            umount /boot
        fi
    fi
}

pkg_prerm_${PN}() {
    if grep dm /etc/hostname > /dev/null ; then
        if [ -z "$D" ]
        then
            if mountpoint -q /boot
            then
                mount -o remount,rw,compr=none /boot
            else
                mount -t jffs2 -o rw,compr=none mtd:'boot partition' /boot
            fi
        fi
    fi    
}

pkg_postrm_${PN}() {
    if grep dm /etc/hostname > /dev/null ; then
        if [ -z "$D" ]
        then
            umount /boot
        fi
    fi    
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/boot /usr/share /etc/init.d"
