PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit cmake

SUMMARY = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES:CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "${AUTOREV}"
SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/ipk/source.arm;module=emus_oscam;protocol=http"
SRCREV_FORMAT = "${PV}"

SRC_URI += "svn://svn.streamboard.tv/oscam;protocol=https;module=trunk;scmdata=keep;externals=nowarn"
SRC_URI += "file://config.patch"

E = "${WORKDIR}/emus_oscam"

DEPENDS = "libusb openssl libdvbcsa"

S = "${WORKDIR}/trunk"

EXTRA_OECMAKE:arm += " -DOSCAM_SYSTEM_NAME=FriendlyARM -DHAVE_LIBDVBCSA=1 -DSTATIC_LIBDVBCSA=1 -DSTATIC_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWEBIF=1 -DWITH_STAPI=0 -DHAVE_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWITH_SSL=1 -DCLOCKFIX=0 -DMODULE_CONSTCW=1 -DHAVE_PCSC=0 -DWITH_EMU=0"
EXTRA_OECMAKE:mipsel += " -DOSCAM_SYSTEM_NAME=FriendlyARM -DHAVE_LIBDVBCSA=1 -DSTATIC_LIBDVBCSA=1 -DSTATIC_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWEBIF=1 -DWITH_STAPI=0 -DHAVE_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWITH_SSL=1 -DCLOCKFIX=0 -DMODULE_CONSTCW=1 -DHAVE_PCSC=0 -DWITH_EMU=0"
EXTRA_OECMAKE:sh4 += " -DOSCAM_SYSTEM_NAME=FriendlyARM -DHAVE_LIBDVBCSA=1 -DSTATIC_LIBDVBCSA=1 -DSTATIC_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWEBIF=1 -DWITH_STAPI=0 -DHAVE_LIBUSB=1 -DSTATIC_LIBUSB=1 -DWITH_SSL=1 -DCLOCKFIX=0 -DMODULE_CONSTCW=1 -DHAVE_PCSC=0 -DWITH_EMU=0"

EXTRA_OECMAKE:arm += " -DWITH_ARM_NEON=1"

do_install() {
    install -d ${D}/bin
    install -m 0755 ${WORKDIR}/build/oscam ${D}/bin/oscam
    cd ${E}
    cp -a _path_/keys ${D}/
    cp -a _path_/etc ${D}/

    SVNVERSION=$(svnversion ${WORKDIR}/trunk)
    sed "s/Description:.*/Description: Latest Version $SVNVERSION of OScam/" -i ${E}/CONTROL/control
}

FILES:${PN} = "/bin /etc /keys"
INSANE_SKIP:${PN} = "already-stripped"

python populate_packages:prepend() {
    def getControlLines(mydir, d, package):
        packagename = package[-1]

        import os

        try:
            print("package1 ", package)
            if(len(package) != 4):
                print("5 return")                
                return
            section = package[2]

            path = mydir + "/CONTROL/control"
            print("path ", path)

            src = open(path).read()
            print("src ", src)
        except IOError:
            return

        for line in src.split("\n"):
            rev = bb.data.expand('${SRCPV}', d)
            box = bb.data.expand('${MACHINEBUILD}', d)
            pr = bb.data.expand('${PR}', d)
            workdir = bb.data.expand('${WORKDIR}', d)

            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            print("full_package ", full_package)

            filename = full_package + '_' + rev + '-' + pr + '_' + box
            print("filename ", filename)

            cmd = 'ls -al ' + mydir + '/preview/prev.png'
            print("cmd1 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'mkdir -p ' + workdir + '/deploy-png/' + box
            print("cmd2 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'cp -a ' + mydir + '/preview/prev.png ' + workdir + '/deploy-png/' + box + '/' + filename + '.png'
            print("cmd3 ", cmd)
            print(" ")
            os.system(cmd)

            if line.startswith('Description: '):
                print("found decription ", line[13:])
                d.setVar('DESCRIPTION:' + full_package, line[13:])
                d.setVar('SUMMARY:' + full_package, line[13:])
            elif line.startswith('Showname: '):
                print("found showname ", line[10:])
                cmd = 'echo "' + line[10:] + '" > ' + workdir + '/deploy-png/' + box + '/' + filename + '.showname'
                print("cmd4 ", cmd)
                print(" ")
                os.system(cmd)
                d.setVar('MAINTAINER:' + full_package, line[10:])
            elif line.startswith('Usepath: '):
                print("found Usepath ", line[9:])
                cmd = 'echo "' + line[9:] + '" > ' + workdir + '/deploy-png/' + box + '/' + filename + '.usepath'
                print("cmd5 ", cmd)
                print(" ")
                os.system(cmd)
            elif line.startswith('Homepage: '):
                d.setVar('HOMEPAGE:' + full_package, line[10:])
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER:' + full_package, line[12:])

            postinstfile = mydir + "/CONTROL/postinst"
            postinst = open(postinstfile).read()
            print("postinst ", postinst)
            d.setVar('pkg_postinst:' + full_package, postinst)

            postrmfile = mydir + "/CONTROL/postrm"
            postrm = open(postrmfile).read()
            print("postrm ", postrm)
            d.setVar('pkg_postrm:' + full_package, postrm)

            preinstfile = mydir + "/CONTROL/preinst"
            preinst = open(preinstfile).read()
            print("preinst ", preinst)
            d.setVar('pkg_preinst:' + full_package, preinst)

            prermfile = mydir + "/CONTROL/prerm"
            prerm = open(prermfile).read()
            print("prerm ", prerm)
            d.setVar('pkg_prerm:' + full_package, prerm)

    mydir = bb.data.expand('${E}', d)
    print("mydir ", mydir)

    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

do_package_qa() {
}

do_package_write_ipk:append() {
    bb.process.run("cp -a ../deploy-png/* .")
}

pkg_preinst:${PN}:prepend() {
echo preinst
echo pwd `pwd`
echo 1 $1
echo * $*
}

pkg_postinst:${PN}:prepend() {
echo postinst
echo pwd `pwd`
echo 1 $1
echo * $*
}

pkg_postrm:${PN}:prepend() {
echo postrm
echo pwd `pwd`
echo 1 $1
echo * $*
}

pkg_prerm:${PN}:prepend() {
echo prerm
echo pwd `pwd`
echo 1 $1
echo * $*
}

#fetch allways
do_fetch[nostamp] = "1"
#build allways
#do_configure[nostamp] = "1"
do_install[vardepsexclude] += "DATE"

